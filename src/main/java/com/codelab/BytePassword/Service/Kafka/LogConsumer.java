package com.codelab.BytePassword.Service.Kafka;

import com.codelab.BytePassword.Messages.ErrorMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static com.codelab.BytePassword.Constant.Constants.TOPIC;

@Slf4j
public class LogConsumer {

    public static void producer(){
        Properties props= new Properties();

            props.put("bootstrap.servers", "localhost:9092");
            props.put("group.id", "log_group");
            props.put("auto.offset.reset", "earliest");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        final Consumer<String, String> consumer =new KafkaConsumer<>(props);

            try{


                consumer.subscribe(Collections.singletonList(TOPIC));

                while(true){
                    ConsumerRecords<String, String> records =consumer.poll(Duration.ofMillis(100));
                    for(ConsumerRecord<String, String> record : records){
                        log.info("Received message: key = {}, value = {} ", record.key(), record.value());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                log.error("Failed to Read from kafka broker {}", e.getLocalizedMessage());
                ErrorMsg.errorMessage("Failed to read from kafka broker!");
            }
            finally {
                consumer.close();
            }

    }

}
