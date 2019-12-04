package com.hao.shirojwt.exception;

import com.hao.shirojwt.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 */
@Slf4j
@RestControllerAdvice
public class BDExceptionHandler {

    @ExceptionHandler(BDException.class)
    public R handleBDException(BDException e) {
        log.debug(e.getMessage(), e);
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R noHandlerFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.NOT_FOUND.value(), "没有找到接口");
    }

    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.FORBIDDEN.value(), "未授权");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Object handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }


    @ExceptionHandler({Exception.class})
    public Object handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误，请联系管理员，" + e.getMessage());
    }

}
