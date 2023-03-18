package com.demo.kafka.context;

import java.util.concurrent.ExecutorService;

/**
 * @author ksssss
 * @Date: 21-2-3 16:16
 * @Description:
 */
public class ThreadPoolParam implements ThreadPoolParamMBean {
    private ThreadPoolMonitor threadPoolMonitor;

    public ThreadPoolParam(ExecutorService es) {
        this.threadPoolMonitor = (ThreadPoolMonitor) es;
    }

    @Override
    public int getActiveCount() {
        return threadPoolMonitor.getActiveCount();
    }

    @Override
    public long getCompletedTaskCount() {
        return threadPoolMonitor.getCompletedTaskCount();
    }

    @Override
    public int getCorePoolSize() {
        return threadPoolMonitor.getCorePoolSize();
    }

    @Override
    public int getMaximumPoolSize() {
        return threadPoolMonitor.getMaximumPoolSize();
    }

    @Override
    public int getPoolSize() {
        return threadPoolMonitor.getPoolSize();
    }

    @Override
    public long getTaskCount() {
        return threadPoolMonitor.getTaskCount();
    }

    @Override
    public long getCostTime() {
        return threadPoolMonitor.getCostTime();
    }

    @Override
    public long getTotalCostTime() {
        return threadPoolMonitor.getTotalCostTime().get();
    }
}
