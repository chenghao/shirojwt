package com.hao.shirojwt.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.hao.shirojwt.util.Constant;
import com.hao.shirojwt.util.IPUtil;
import com.hao.shirojwt.util.R;
import com.hao.shirojwt.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@Component
public class WhiteListInterceptor extends HandlerInterceptorAdapter {

    @Value("${whitelist.open}")
    private boolean whitelistOpen;
    @Value("${whitelist.excludes}")
    private String excludes;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        excludes = StringUtil.clearUnescape(excludes);
        String[] excludeArr = excludes.split(",");

        String url = request.getRequestURL().toString();
        for(String ex: excludeArr){
            if(url.contains(ex)){
                return true;
            }
        }

        boolean bo = false;
        List<String> ips = Constant.whitelists;
        if(whitelistOpen && ips.size() > 0){
            String currIp = IPUtil.getIpByReq();
            if(ips.contains(currIp)){
                bo = true;
            }

            if(!bo){
                R r = R.error("IP受限");
                response.setCharacterEncoding(Constant.UTF8);
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    out.append(JSONObject.toJSONString(r));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
            }
        }else {
            bo = true;
        }

        return bo;
    }

}
