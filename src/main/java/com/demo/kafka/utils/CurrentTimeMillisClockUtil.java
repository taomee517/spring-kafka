package com.demo.kafka.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ksssss
 * @Date: 20-9-22 18:47
 * @Description: 高并发或频繁访问会造成严重的争用会造成System.currentTimeMillis()性能消耗剧增，所以用单个调度线程来处理
 */
public class CurrentTimeMillisClockUtil {
    private long currentTimeMillis;

    public static final CurrentTimeMillisClockUtil INSTANCE = new CurrentTimeMillisClockUtil();

    private CurrentTimeMillisClockUtil() {
        this.currentTimeMillis = System.currentTimeMillis();
        scheduleTick();
    }

    private void scheduleTick() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            currentTimeMillis = System.currentTimeMillis();
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public long now() {
        return currentTimeMillis;
    }
}
