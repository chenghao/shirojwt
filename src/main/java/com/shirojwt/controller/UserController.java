package com.shirojwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shirojwt.annotation.Log;
import com.shirojwt.annotation.enums.BusinessType;
import com.shirojwt.exception.BDException;
import com.shirojwt.model.UserDto;
import com.shirojwt.service.UserService;
import com.shirojwt.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * UserController
 */
@RestController
@RequestMapping("/user")
public class UserController {

    //RefreshToken过期时间
    private String refreshTokenExpireTime = PropertiesUtil.getInstance().getProperty("refreshTokenExpireTime");

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private UserService userService;


    /**
     * 获取用户列表
     * @param 
     */
    @Log(title = "获取用户列表", businessType = BusinessType.QUERY)
    @GetMapping
    @RequiresPermissions(logical = Logical.AND, value = {"user:view"})
    public PageResult<UserDto> user(Integer page, Integer limit) {
        if (page == null || page < 0) page = Constant.page;
        if (limit == null || limit < 0) limit = Constant.limit;

        Page<UserDto> userPage = new Page<>(page, limit);
        EntityWrapper<UserDto> wrapper = new EntityWrapper<>();
        wrapper.orderBy("reg_time", false);

        userService.selectPage(userPage, wrapper);
        List<UserDto> userList = userPage.getRecords();

        return new PageResult<>(userList, userPage.getTotal());
    }

    /**
     * 获取在线用户(查询Redis中的RefreshToken)
     * @param 
     */
    @Log(title = "获取在线用户", businessType = BusinessType.QUERY)
    @GetMapping("/online")
    @RequiresPermissions(logical = Logical.AND, value = {"user:view"})
    public R online() {
        List<Object> userDtos = new ArrayList<>();
        // 查询所有Redis键
        Set<String> keys = JedisUtil.keysS(Constant.PREFIX_SHIRO_REFRESH_TOKEN + "*");
        for (String key : keys) {
            if (JedisUtil.exists(key)) {
                // 根据:分割key，获取最后一个字符(帐号)
                String[] strArray = key.split(":");
                UserDto userDto = new UserDto();
                userDto.setAccount(strArray[strArray.length - 1]);
                userDto = userService.selectOne(new EntityWrapper<>(userDto));
                // 设置登录时间
                userDto.setLoginTime(new Date(Long.parseLong(JedisUtil.getObject(key).toString())));
                userDtos.add(userDto);
            }
        }
        if (userDtos == null || userDtos.size() <= 0) {
            throw new BDException("查询失败(Query Failure)");
        }
        return R.ok(userDtos);
    }

    /**
     * 登录授权
     * @param userDto
     */
    @Log(title = "登录授权")
    @PostMapping("/login")
    public R login(@RequestBody UserDto userDto, HttpServletResponse httpServletResponse) {
        // 查询数据库中的帐号信息
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setAccount(userDto.getAccount());
        userDtoTemp.setPassword(AesCipherUtil.enCrypto(userDto.getAccount() + userDto.getPassword()));
        userDtoTemp = userService.selectOne(new EntityWrapper<>(userDtoTemp));
        if (userDtoTemp == null) {
            throw new BDException("该帐号不存在(The account does not exist.)");
        }
        // 密码进行AES解密
        String key = AesCipherUtil.deCrypto(userDtoTemp.getPassword());
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(userDto.getAccount() + userDto.getPassword())) {
            // 清除可能存在的Shiro权限信息缓存
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + userDto.getAccount())) {
                JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + userDto.getAccount());
            }
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userDto.getAccount(), currentTimeMillis,
                    Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(userDto.getAccount(), currentTimeMillis);
            httpServletResponse.setHeader("Authorization", token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
            return R.ok(token);
        } else {
            throw new BDException("帐号或密码错误(Account or Password Error.)");
        }
    }

    /**
     * 测试登录
     * @param
     */
    @Log(title = "测试登录")
    @GetMapping("/article")
    public R article() {
        Subject subject = SecurityUtils.getSubject();
        // 登录了返回true
        if (subject.isAuthenticated()) {
            return R.ok(HttpStatus.OK.value(), "您已经登录了(You are already logged in)");
        } else {
            return R.ok(HttpStatus.OK.value(), "你是游客(You are guest)");
        }
    }

    /**
     * 测试登录注解(@RequiresAuthentication和subject.isAuthenticated()返回true一个性质)
     * @param
     */
    @Log(title = "测试登录注解")
    @GetMapping("/article2")
    @RequiresAuthentication
    public R requireAuth() {
        return R.ok(HttpStatus.OK.value(), "您已经登录了(You are already logged in)");
    }

    /**
     * 获取当前登录用户信息
     * @param
     */
    @Log(title = "获取当前登录用户信息")
    @GetMapping("/info")
    @RequiresAuthentication
    public R info() {
        // 获取当前登录用户
        UserDto userDto = userUtil.getUser();
        // 获取当前登录用户Token
        String token = userUtil.getToken();
        // 获取当前登录用户Account
        String account = userUtil.getAccount();

        Map<String, Object> map = new HashMap<>();
        map.put("user", userDto);
        map.put("token", token);
        map.put("account", account);

        return R.ok(JSONObject.toJSONString(map));
    }


    /**
     * 新增用户
     * @param userDto
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author dolyw.com
     * @date 2018/8/30 10:42
     */
    /*@PostMapping
    @RequiresPermissions(logical = Logical.AND, value = {"user:edit"})
    public ResponseBean add(@Validated(UserEditValidGroup.class) @RequestBody UserDto userDto) {
        // 判断当前帐号是否存在
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setAccount(userDto.getAccount());
        userDtoTemp = userService.selectOne(userDtoTemp);
        if (userDtoTemp != null && StringUtil.isNotBlank(userDtoTemp.getPassword())) {
            throw new CustomUnauthorizedException("该帐号已存在(Account exist.)");
        }
        userDto.setRegTime(new Date());
        // 密码以帐号+密码的形式进行AES加密
        if (userDto.getPassword().length() > Constant.PASSWORD_MAX_LEN) {
            throw new CustomException("密码最多8位(Password up to 8 bits.)");
        }
        String key = AesCipherUtil.enCrypto(userDto.getAccount() + userDto.getPassword());
        userDto.setPassword(key);
        int count = userService.insert(userDto);
        if (count <= 0) {
            throw new CustomException("新增失败(Insert Failure)");
        }
        return new ResponseBean(HttpStatus.OK.value(), "新增成功(Insert Success)", userDto);
    }*/

    /**
     * 更新用户
     * @param userDto
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author dolyw.com
     * @date 2018/8/30 10:42
     */
    /*@PutMapping
    @RequiresPermissions(logical = Logical.AND, value = {"user:edit"})
    public ResponseBean update(@Validated(UserEditValidGroup.class) @RequestBody UserDto userDto) {
        // 查询数据库密码
        UserDto userDtoTemp = new UserDto();
        userDtoTemp.setAccount(userDto.getAccount());
        userDtoTemp = userService.selectOne(userDtoTemp);
        if (userDtoTemp == null) {
            throw new CustomUnauthorizedException("该帐号不存在(Account not exist.)");
        } else {
            userDto.setId(userDtoTemp.getId());
        }
        // FIXME: 如果不一样就说明用户修改了密码，重新加密密码(这个处理不太好，但是没有想到好的处理方式)
        if (!userDtoTemp.getPassword().equals(userDto.getPassword())) {
            // 密码以帐号+密码的形式进行AES加密
            if (userDto.getPassword().length() > Constant.PASSWORD_MAX_LEN) {
                throw new CustomException("密码最多8位(Password up to 8 bits.)");
            }
            String key = AesCipherUtil.enCrypto(userDto.getAccount() + userDto.getPassword());
            userDto.setPassword(key);
        }
        int count = userService.updateByPrimaryKeySelective(userDto);
        if (count <= 0) {
            throw new CustomException("更新失败(Update Failure)");
        }
        return new ResponseBean(HttpStatus.OK.value(), "更新成功(Update Success)", userDto);
    }*/

    /**
     * 删除用户
     * @param id
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author dolyw.com
     * @date 2018/8/30 10:43
     */
    /*@DeleteMapping("/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"user:edit"})
    public ResponseBean delete(@PathVariable("id") Integer id) {
        int count = userService.deleteByPrimaryKey(id);
        if (count <= 0) {
            throw new CustomException("删除失败，ID不存在(Deletion Failed. ID does not exist.)");
        }
        return new ResponseBean(HttpStatus.OK.value(), "删除成功(Delete Success)", null);
    }*/

    /**
     * 剔除在线用户
     * @param id
     * @return com.wang.model.common.ResponseBean
     * @author dolyw.com
     * @date 2018/9/6 10:20
     */
    /*@DeleteMapping("/online/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"user:edit"})
    public ResponseBean deleteOnline(@PathVariable("id") Integer id) {
        UserDto userDto = userService.selectByPrimaryKey(id);
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userDto.getAccount())) {
            if (JedisUtil.delKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userDto.getAccount()) > 0) {
                return new ResponseBean(HttpStatus.OK.value(), "剔除成功(Delete Success)", null);
            }
        }
        throw new CustomException("剔除失败，Account不存在(Deletion Failed. Account does not exist.)");
    }*/
}