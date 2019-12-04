package com.hao.shirojwt.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Component
public class ThreadPoolUtil {

    @Autowired
    private ExecutorService executorService;

    public void execute(Runnable runnable){
        executorService.execute(runnable);
    }

    public Future<?> submit(Runnable runnable){
        Future future = executorService.submit(runnable);
        return future;
    }

    public <T> Future submit(Callable<T> callable){
        Future future = executorService.submit(callable);
        return future;
    }

    public <T> List<Future<T>> invokeAll( ArrayList<Callable<T>> callables){
        List<Future<T>> futures = null;
        try{
            futures = executorService.invokeAll(callables);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return futures;
    }

}
