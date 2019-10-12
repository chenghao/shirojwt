package com.shirojwt.exception;

import com.shirojwt.util.R;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class BDExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @ExceptionHandler(BDException.class)
    public R handleBDException(BDException e) {
        logger.debug(e.getMessage(), e);
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R noHandlerFoundException(NoHandlerFoundException e) {
        logger.error(e.getMessage(), e);
        return R.error(HttpStatus.NOT_FOUND.value(), "没有找到接口");
    }

    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(AuthorizationException e) {
        logger.error(e.getMessage(), e);
        return R.error(HttpStatus.FORBIDDEN.value(), "未授权");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Object handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }


    @ExceptionHandler({Exception.class})
    public Object handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，请联系管理员，" + e.getMessage());
    }

}
