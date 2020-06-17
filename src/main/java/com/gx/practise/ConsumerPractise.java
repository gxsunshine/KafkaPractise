package com.gx.practise;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import sun.awt.windows.ThemeReader;

import java.util.*;

/**
 * @ClassName ConsumerPractise
 * @Description kafka 消费者客户端
 * @Authtor guoxiang
 * @Date 2020/6/8 10:43
 **/
public class ConsumerPractise {
    public static Properties props = new Properties();

    public static void main(String[] args) {
//        autoCommitOffset();
//        manualOffset();
        KafkaConsumerRunner kafkaConsumerRunner = new KafkaConsumerRunner();
        Thread thread01 = new Thread(kafkaConsumerRunner, "thread01");
        Thread thread02 = new Thread(kafkaConsumerRunner, "thread02");
        Thread thread03 = new Thread(kafkaConsumerRunner, "thread03");
        thread01.start();
        thread02.start();
        thread03.start();
    }

    /**
     * 初始化kafka的配置
     */
    public static void init(){
        // 集群是通过配置bootstrap.servers指定一个或多个broker。不用指定全部的broker，它将自动发现集群中的其余的borker（最好指定多个，万一有服务器故障）。
        props.put("bootstrap.servers", "localhost:9092");
        // 消费者组名
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        // deserializer设置如何把byte转成object类型。StringDeserializer是String解析器，告诉获取到的消息的key和value只是简单个string类型。
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    /**
     * 自动提交偏移量
     */
    public static void autoCommitOffset(){
        init();
        // 设置enable.auto.commit,偏移量由auto.commit.interval.ms控制自动提交的频率。
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("summer", "summer2"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }

    /**
     * 手动提交偏移量 - 防止异常导致数据丢失
     * 不需要定时的提交offset，可以自己控制offset，当消息认为已消费过了，这个时候再去提交它们的偏移量。这个很有用的，当消费的消息结合了一些处理逻辑，这个消息就不应该认为是已经消费的，直到它完成了整个处理。
     */
    public static void manualOffset(){
        init();
        // 关闭自动提交偏移量
        props.put("enable.auto.commit", "false");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("summer"));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(10);
            for (ConsumerRecord<String, String> record : records){
               buffer.add(record);
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            if(buffer.size() >= minBatchSize){
                insertIntoDb(buffer);
                // 手动提交偏移量，如果这批被消费的数据业务处理出现异常，不会提交偏移量，下次还会读取这批数据，防止异常导致数据丢失
                consumer.commitSync();
                buffer.clear();
            }
        }
    }

    /**
     * 把被消费的数据存入DB - 模拟数据的其他业务处理
     * @param buffer
     */
    public static void insertIntoDb(List<ConsumerRecord<String, String>>  buffer) {
        // 使用随机数，随机插入批次出现异常，模拟被消费的数据处理其他业务逻辑时抛出异常
        Random random = new Random();
        int errorValue = random.nextInt(6);
        System.out.println("开始往数据库插入数据");
        if(errorValue == 3){
            throw new ArrayIndexOutOfBoundsException("产生异常");
        }
        System.out.println("数据插入数据库完成");
    }
}

/**
 * 多线程消费
 */
class KafkaConsumerRunner implements Runnable{
    @Override
    public void run() {
        ConsumerPractise.init();
        // 设置enable.auto.commit,偏移量由auto.commit.interval.ms控制自动提交的频率。
        ConsumerPractise.props.put("enable.auto.commit", "true");
        ConsumerPractise.props.put("auto.commit.interval.ms", "100");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(ConsumerPractise.props);
        consumer.subscribe(Arrays.asList("summer", "summer2"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("thread = %s, offset = %d, key = %s, value = %s%n", Thread.currentThread().getName(), record.offset(), record.key(), record.value());
            }
        }
    }
}
