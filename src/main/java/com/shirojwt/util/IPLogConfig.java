package com.shirojwt.util;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class IPLogConfig extends ClassicConverter {
    private Logger log = LoggerFactory.getLogger(this.getClass());

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
