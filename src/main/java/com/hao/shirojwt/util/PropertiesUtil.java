package com.hao.shirojwt.util;

import com.hao.shirojwt.exception.BDException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Properties工具
 */
@Slf4j
public class PropertiesUtil {

    private final Properties PROP = new Properties();

    private static final PropertiesUtil instance = new PropertiesUtil();
    private PropertiesUtil() {
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getResourceAsStream("/config.properties");
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            PROP.load(bf);
        } catch (IOException e) {
            log.error("PropertiesUtil工具类读取配置文件出现IOException异常:" + e.getMessage());
            throw new BDException("PropertiesUtil工具类读取配置文件出现IOException异常:" + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("PropertiesUtil工具类读取配置文件出现IOException异常:" + e.getMessage());
                throw new BDException("PropertiesUtil工具类读取配置文件出现IOException异常:" + e.getMessage());
            }
        }
    }

    public static PropertiesUtil getInstance() {
        return instance;
    }

    /**
     * 根据key读取对应的value
     * @param key
     * @return java.lang.String
     */
    public String getProperty(String key){
        String val = PROP.getProperty(key);
        if(StringUtil.isNotBlank(val)){
            val = val.trim();
        }
        return val;
    }
}
