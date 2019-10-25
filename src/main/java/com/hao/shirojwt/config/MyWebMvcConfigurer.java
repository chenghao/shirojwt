package com.hao.shirojwt.config;

import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;


@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    // 支持跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    // 支持PUT请求
    @Bean
    public HttpPutFormContentFilter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }

    @Bean
    public DbSearcher getDbSearcher() throws DbMakerConfigException, IOException {
        String dbPath = MyWebMvcConfigurer.class.getResource("/ip2region.db").getFile();
        DbConfig config = new DbConfig();
        DbSearcher searcher = new DbSearcher(config, dbPath);
        return searcher;
    }
}