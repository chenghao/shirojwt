package com.shirojwt.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
@EnableConfigurationProperties({ThreadPoolProperties.class})
public class ThreadPoolConfiguration {
    @Autowired
    private ThreadPoolProperties properties;

    @Bean
    public ExecutorService getThreadPool(){
        ExecutorService executorService = new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(properties.getQueueNum()),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        return executorService;
    }
}
