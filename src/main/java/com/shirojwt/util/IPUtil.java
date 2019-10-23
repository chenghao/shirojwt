package com.shirojwt.util;

import com.shirojwt.config.SpringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtil {
    private static final Logger log = LoggerFactory.getLogger(IPUtil.class);

    /**
     * 根据请求获取ip
     * @return
     */
    public static String getIpByReq() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        return getIpByReq(request);
    }
    public static String getIpByReq(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }
        return ip;
    }

    public static String getAddress() {
        return getAddress(getIpByReq());
    }
    public static String getAddress(String ip) {
        try {
            DataBlock dataBlock = (SpringUtils.getBean(DbSearcher.class)).memorySearch(ip);
            String info = dataBlock.getRegion();
            if (info == null) {
                return null;
            }

            // 中国|0|北京|北京市|联通
            String[] split = info.split("\\|");
            StringBuffer sb = new StringBuffer();
            // 国家
            String contry = split[0];
            if(!"中国".equals(contry) && !"0".equals(contry)){
                sb.append(contry);
            }
            // 省
            String province = split[2];
            if (!province.equals("0")) {
                sb.append(province);
            }
            // 市
            String city = split[3];
            if (!city.equals("0")) {
                sb.append(city);
            }
            // 运营商
            String operator = split[4];
            if (!operator.equals("0")) {
                sb.append("-");
                sb.append(operator);
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("获取ip详细地址失败, " + e.getMessage());
        }
        return "";
    }

}
