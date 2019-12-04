package com.hao.shirojwt.annotation;

import com.google.common.collect.ImmutableList;
import com.hao.shirojwt.annotation.enums.LimitType;
import com.hao.shirojwt.exception.BDException;
import com.hao.shirojwt.util.IPUtil;
import com.hao.shirojwt.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * 接口限流
 *
 */
@Slf4j
@Aspect
@Component
public class LimitAspect {

    @Pointcut("@annotation(com.hao.shirojwt.annotation.Limit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    //@Before("pointcut()")
    public Object Around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Limit limitAnnotation = method.getAnnotation(Limit.class);

        String ip = IPUtil.getIpByReq();
        // 获取注解上的参数
        LimitType limitType = limitAnnotation.limitType();
        String name = limitAnnotation.name();
        int limitPeriod = limitAnnotation.period();
        int limitCount = limitAnnotation.count();
        String prefix = limitAnnotation.prefix();

        String key;
        switch (limitType) {
            case IP:
                key = ip;
                break;
            case CUSTOMER:
                key = limitAnnotation.key();
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }

        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(prefix + ":", key, ip));
        String luaScript = buildLuaScript();

        Jedis jedis = JedisUtil.getJedis();
        Object result = jedis.evalsha(jedis.scriptLoad(luaScript), keys, Arrays.asList(limitPeriod + "", limitCount + ""));
        int count = 1;
        if(result != null) count = Integer.parseInt(result.toString());
        log.info("IP:{} 第 {} 次访问key为 {}，描述为 [{}] 的接口", ip, count, keys, name);
        if (count <= limitCount) {
            return point.proceed();
        } else {
            throw new BDException("接口访问超出频率限制");
        }
    }

    /**
     * 限流脚本
     * 调用的时候不超过阈值，则直接返回并执行计算器自加。
     *
     * @return lua脚本
     */
    private String buildLuaScript() {
        return "local c" +
                "\nc = redis.call('get',KEYS[1])" +
                "\nif c and tonumber(c) > tonumber(ARGV[1]) then" +
                "\nreturn c;" +
                "\nend" +
                "\nc = redis.call('incr',KEYS[1])" +
                "\nif tonumber(c) == 1 then" +
                "\nredis.call('expire',KEYS[1],ARGV[2])" +
                "\nend" +
                "\nreturn c;";
    }
}
