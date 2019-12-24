package com.hao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.hao"})
@EnableCaching
public class StartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartupApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    demo启动成功      ヾ(◍°∇°◍)ﾉﾞ\n");
    }


}
