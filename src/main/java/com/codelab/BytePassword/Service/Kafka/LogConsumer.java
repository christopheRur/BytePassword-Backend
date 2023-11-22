package com.codelab.BytePassword.Service.Kafka;

import com.codelab.BytePassword.Messages.ErrorMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

import static com.codelab.BytePassword.Constant.Constants.TOPIC;

@Slf4j
public class LogConsumer {

    public static void dataConsumer() {
        Runnable kafkaConsumerService = () -> {
            Properties props = new Properties();
            // Kafka consumer configuration
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
            props.put("auto.offset.reset", "earliest");


            try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
                consumer.subscribe(Collections.singletonList(TOPIC));

                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        log.info("====>Received message:key{}  value = {} ", record.key(), record.value());
                        // Commit the offset after processing the message
                        consumer.commitSync(Collections.singletonMap(
                                new TopicPartition(record.topic(), record.partition()),
                                new OffsetAndMetadata(record.offset() + 1)));

                    }

                }

            } catch (Exception e) {
                log.error("Failed to read from Kafka broker: {}", e.getMessage());
            }
        };

        Thread kafkaConsumerThread = new Thread(kafkaConsumerService);
        kafkaConsumerThread.start();
    }

}
