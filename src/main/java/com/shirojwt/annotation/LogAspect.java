package com.shirojwt.annotation;

import com.alibaba.fastjson.JSONObject;
import com.shirojwt.annotation.enums.BusinessStatus;
import com.shirojwt.model.UserDto;
import com.shirojwt.model.entity.OperLog;
import com.shirojwt.util.HttpContextUtils;
import com.shirojwt.util.IPUtil;
import com.shirojwt.util.ThreadPoolUtil;
import com.shirojwt.util.UserUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录处理
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private ThreadPoolUtil threadPoolUtil;
    @Autowired
    private UserUtil userUtil;


    // 配置织入点
    @Pointcut("@annotation(com.shirojwt.annotation.Log)")
    public void logPointCut() { }

    /**
     * 处理完请求后执行
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            Integer userId = null;
            try{
                UserDto userDto = userUtil.getUser();
                if(userDto != null) userId = userDto.getId();
            }catch (Exception ex){ }

            // *========数据库日志=========*//
            OperLog operLog = new OperLog();
            operLog.setOperId(userId);
            // 请求的地址
            String ip = IPUtil.getIpByReq();
            String address = IPUtil.getAddress(ip);
            operLog.setOperIp(ip);
            operLog.setOperLocation(address);
            operLog.setOperUrl(HttpContextUtils.getHttpServletRequest().getRequestURI());
            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.name());
                operLog.setErrorMsg(e.getMessage());
            }else{
                operLog.setStatus(BusinessStatus.SUCCESS.name());
                operLog.setErrorMsg("");
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);

            // 直接记录日志
            log.info("日志记录： " + JSONObject.toJSONString(operLog));

            // 保存数据库
            /*threadPoolUtil.execute(new Runnable() {
                @Override
                public void run() {
                    (SpringUtils.getBean(OperLogService.class)).insert(operLog);
                }
            });*/
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error(exp.getMessage(), e);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param log 日志
     * @param operLog 操作日志
     * @throws Exception
     */
    private void getControllerMethodDescription(Log log, OperLog operLog) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().name());
        // 设置标题
        operLog.setTitle(log.title());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     * @param operLog
     */
    private void setRequestValue(OperLog operLog) {
        Map<String, String[]> map = HttpContextUtils.getHttpServletRequest().getParameterMap();
        String params = JSONObject.toJSONString(map);
        operLog.setOperParam(params);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
