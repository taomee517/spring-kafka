//package com.demo.kafka.consumer;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.demo.kafka.constants.KafkaTopic;
//import com.demo.kafka.entity.po.Device;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Objects;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.SynchronousQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Component
//public class ParallelTaskConsumer{
//
//    /**
//     * 创建了一个最大容量为20的线程池，其中有两个参数需要注意一下:
//     *  1. 我们使用了了零容量的SynchronousQueue，一进一出，避免队列里缓冲数据，这样在系统异常关闭时，就能排除因为阻塞队列丢消息的可能。
//     *  2. 然后使用了CallerRunsPolicy饱和策略，使得多线程处理不过来的时候，能够阻塞在kafka的消费线程上。
//     * 然后，我们将真正处理业务的逻辑放在任务中多线程执行，每次执行完毕，我们都手工的commit一次ack，表明这条消息我已经处理了。
//     * 由于是线程池认领了这些任务，顺序性是无法保证的，可能有些任务没有执行完毕，后面的任务就已经把它的offset给提交了。
//     *
//     */
//    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
//            5,
//            20,
//            10,
//            TimeUnit.SECONDS,
//            new SynchronousQueue<>(),
//            new ThreadPoolExecutor.CallerRunsPolicy()
//    );
//
//
//    @KafkaListener(topics={KafkaTopic.PARALLEL_TEST_TOPIC}, containerFactory = "parallelConsumerFactory", groupId = "parallel-group")
//    public void listen(List<ConsumerRecord<String,Device>> records, Acknowledgment ack){
//        if (Objects.nonNull(ack)) {
//            ack.acknowledge();
//        }
//
////        CompletableFuture.runAsync(new Runnable() {
////            @Override
////            public void run() {
////                processEachRecord(record);
////            }
////        }, executor);
//
//        if (records.size() > 0) {
//            Iterator<ConsumerRecord<String, Device>> iterator = records.iterator();
//            while (iterator.hasNext()) {
//                ConsumerRecord record = iterator.next();
//                CompletableFuture.runAsync(new Runnable() {
//                    @Override
//                    public void run() {
//                        processEachRecord(record);
//                    }
//                }, executor);
//            }
//        }
//    }
//
//
//    void processEachRecord(ConsumerRecord<String,Device> record){
//        Device device = record.value();
////        log.info("收到proto-device消息：device = {}", device);
//        long now = System.currentTimeMillis();
//        long recordTimeStamp = record.timestamp();
//        long offset = now - recordTimeStamp;
//        if(offset > 10000){
//            log.error("消息处理延迟超过10秒, index = {}", device.getParas());
//            return;
//        }
////        Object integers = Arrays.asList(1, 2);
//        String arr = "{\"source\":[1,2]}";
//        JSONObject jsonObject = JSON.parseObject(arr);
//        Object source = jsonObject.get("source");
//        byte[] bytes = (byte[]) source;
//        System.out.println(bytes.length);
//
//        try {
//            //每个业务处理耗时 2 秒
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("设备上线,相关业务处理完成：device = {}", device);
//    }
//
//
//
//}
