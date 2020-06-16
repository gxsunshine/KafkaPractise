package com.gx.practise;

import org.apache.kafka.clients.producer.*;
import org.junit.Test;

import java.util.Properties;

/**
 * @ClassName ProducerPractise
 * @Description kafka 生产者客户端练习
 * @Authtor guoxiang
 * @Date 2020/6/8 10:11
 **/
public class ProducerPractise {
    private Properties props = new Properties();

    @Test
    public void producer() {

        initProps();
        Producer<String, String> producer = new KafkaProducer<>(props);
        for(int i = 0; i < 10000; i++)
            producer.send(new ProducerRecord("summer", "key:"+Integer.toString(i), "value:"+Integer.toString(i)));
//            producer.send(new ProducerRecord("summer", "value:"+Integer.toString(i)));

        producer.close();
        System.out.println("生产者生成数据完毕");
    }

    @Test
    public void syncMethod(){
        initProps();
        Producer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord record = new ProducerRecord("summer", "name", "gx");

        producer.send(record,
            new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if(e != null)
                        e.printStackTrace();
                    System.out.println("The offset of the record we just sent is: " + metadata.offset());
                }
            });

        producer.close();
        System.out.println("生产者生成数据完毕");
    }

    /**
     * 初始化配置文件
     */
    public  void initProps(){

        props.put("bootstrap.servers", "localhost:9092");
        // 判别请求是否为完整的条件(判断是不是成功发送了),指定了“all”将会阻塞消息，这种设置性能最低，但是是最可靠的。
        props.put("acks", "all");
        // 自动重试次数，如果请求失败，生产者会自动重试，我们指定是0次，如果启用重试，则会有重复消息的可能性。
        props.put("retries", 0);
        // (生产者)缓存每个分区未发送的消息。缓存的大小是通过 batch.size 配置指定的。值较大的话将会产生更大的批。并需要更多的内存（因为每个“活跃”的分区都有1个缓冲区）。
        props.put("batch.size", 16384);
        /**
         *
         默认缓冲可立即发送，即便缓冲空间还没有满，但是，如果你想减少请求的数量，可以设置linger.ms大于0。
         这将指示生产者发送请求之前等待一段时间，希望更多的消息填补到未满的批中。
         这类似于TCP的算法，例如上面的代码段，可能100条消息在一个请求发送，
         因为我们设置了linger(逗留)时间为1毫秒，然后，如果我们没有填满缓冲区，
         这个设置将增加1毫秒的延迟请求以等待更多的消息。需要注意的是，
         在高负载下，相近的时间一般也会组成批，即使是 linger.ms=0。在不处于高负载的情况下，
         如果设置比0大，以少量的延迟代价换取更少的，更有效的请求。
         */
        props.put("linger.ms", 100);
        // 生产者可用的缓存总量,如果消息发送速度比其传输到服务器的快，将会耗尽这个缓存空间。当缓存空间耗尽，其他发送调用将被阻塞，阻塞时间的阈值通过max.block.ms设定，之后它将抛出一个TimeoutException。
        props.put("buffer.memory", 33554432);
        // 将用户提供的key和value对象ProducerRecord转换成字节，可以使用附带的ByteArraySerializaer或StringSerializer处理简单的string或byte类型。
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

}

