package com.hao.shirojwt.util;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class IPLogConfig extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        try {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            if(ra != null){
                HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
                return IPUtil.getIpByReq(request);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
