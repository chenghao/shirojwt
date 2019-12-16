package com.hao.shirojwt.config;

import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;


@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurationSupport {

    // 支持跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webapp/**").addResourceLocations("classpath:/webapp/");
        registry.addResourceHandler("/webapp2/**").addResourceLocations("classpath:/webapp2/");
    }

    @Bean
    public DbSearcher getDbSearcher() throws DbMakerConfigException, IOException {
        String dbPath = MyWebMvcConfigurer.class.getResource("/ip2region.db").getFile();
        DbConfig config = new DbConfig();
        DbSearcher searcher = new DbSearcher(config, dbPath);
        return searcher;
    }
}