package com.demo.kafka.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 罗涛
 * @title ExecutorManager
 * @date 2020/12/18 16:19
 */
@Slf4j
@Component
public class ExecutorManager implements Closeable {
    /**
     * 创建了一个最大容量为核心数*2的线程池，其中有两个参数需要注意一下:
     * 1. 我们使用了了零容量的SynchronousQueue，一进一出，避免队列里缓冲数据，这样在系统异常关闭时，就能排除因为阻塞队列丢消息的可能。
     * 2. 然后使用了CallerRunsPolicy饱和策略，使得多线程处理不过来的时候，能够阻塞在kafka的消费线程上。
     * 然后，我们将真正处理业务的逻辑放在任务中多线程执行，每次执行完毕，我们都手工的commit一次ack，表明这条消息我已经处理了。
     * 由于是线程池认领了这些任务，顺序性是无法保证的，可能有些任务没有执行完毕，后面的任务就已经把它的offset给提交了。
     */
//    public ThreadPoolExecutor executor = new ThreadPoolExecutor(
//            5,
//            20,
//            10,
//            TimeUnit.SECONDS,
//            new SynchronousQueue<>(),
//            new ThreadFactory() {
//                private volatile int index = 0;
//                @Override
//                public Thread newThread(@NotNull Runnable r) {
//                    index++;
//                    String name = StringUtils.joinWith("-", "PROCESSOR", index);
//                    return new Thread(r, name);
//                }
//            },
//            new ThreadPoolExecutor.CallerRunsPolicy()
//    );

    private static final int CORE_SIZE = 8;
    private volatile AtomicLong atomicLong = new AtomicLong(0);
    public Map<Integer,ThreadPoolMonitor> executorMap = new ConcurrentHashMap<>(CORE_SIZE);
    public Map<String, Integer> distributeMap = new ConcurrentHashMap<>(CORE_SIZE);

    @PostConstruct
    public void init() {
        for (int i = 0; i < CORE_SIZE; i++) {
            ThreadPoolMonitor threadPoolMonitor = new ThreadPoolMonitor(
                    1,
                    1,
                    20,
                    TimeUnit.SECONDS,
//                    new SynchronousQueue<>(),
                    new LinkedBlockingQueue<>(1024),
                    new ThreadPoolExecutor.CallerRunsPolicy(),
                    "consumer-hash"+i);
            MbeanServerUtil.registerMBean(new ThreadPoolParam(threadPoolMonitor), "threadMonitor:name=ThreadPool");
            executorMap.put(i, threadPoolMonitor);
        }
    }

    @Override
    public void close() throws IOException {
        if(!executorMap.isEmpty()) {
            executorMap.values().forEach(executor->{
                shutdownGracefully(executor, 30, TimeUnit.SECONDS);
            });
        }
    }

    // 参考RocketMQ优雅退出机制
    public void shutdownGracefully(ExecutorService executor, long timeout, TimeUnit timeUnit) {
        // Disable new tasks from being submitted.
        executor.shutdown();
        try {
            // Wait a while for existing tasks to terminate.
            if (!executor.awaitTermination(timeout, timeUnit)) {
                executor.shutdownNow();
                // Wait a while for tasks to respond to being cancelled.
                if (!executor.awaitTermination(timeout, timeUnit)) {
                    log.warn(String.format("%s didn't terminate!", executor));
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted.
            executor.shutdownNow();
            // Preserve interrupt status.
            Thread.currentThread().interrupt();
        }
    }

    public ThreadPoolExecutor getExecutorByHash(String deviceId){
        int executorKey = getHashIndex(deviceId);
        return executorMap.get(executorKey);
    }

    public int getHashIndex(String deviceId){
        Integer existCode = distributeMap.get(deviceId);
        if(Objects.isNull(existCode)){
            long recordCode = atomicLong.getAndIncrement();
            existCode = (int)(recordCode % CORE_SIZE);
            distributeMap.put(deviceId, existCode);
        }
        return existCode;
    }

}
