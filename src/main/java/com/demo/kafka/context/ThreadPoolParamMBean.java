package com.demo.kafka.context;

/**
 * @author ksssss
 * @Date: 21-2-3 16:16
 * @Description:
 */
public interface ThreadPoolParamMBean {
    /**
     * 线程池中正在执行任务的线程数量
     *
     * @return
     */
    int getActiveCount();

    /**
     * 线程池已完成的任务数量
     *
     * @return
     */
    long getCompletedTaskCount();

    /**
     * 线程池的核心线程数量
     *
     * @return
     */
    int getCorePoolSize();

    /**
     * 线程池的最大线程数量
     *
     * @return
     */
    int getMaximumPoolSize();

    /**
     * 线程池当前的线程数量
     *
     * @return
     */
    int getPoolSize();

    /**
     * 线程池需要执行的任务数量
     *
     * @return
     */
    long getTaskCount();

    /**
     * 线程执行任务耗时
     *
     * @return
     */
    long getCostTime();

    /**
     * 线程执行总耗时
     *
     * @return
     */
    long getTotalCostTime();
}
