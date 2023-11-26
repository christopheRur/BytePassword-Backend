package com.codelab.BytePassword.Service.Kafka;

import com.codelab.BytePassword.Messages.ErrorMsg;
import com.codelab.BytePassword.Messages.SuccessMsg;
import com.codelab.BytePassword.Utils.ToolBox;
import com.codelab.BytePassword.model.BytePwd;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import static com.codelab.BytePassword.Constant.Constants.TOPIC;

@Slf4j
public class LogProducer {
    public static void produceLogs(BytePwd message) {

        //-------------------------------------------------
        message.setTimestamp(ToolBox.stampTimeOfLogs());
        //-------------------------------------------------

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try {

            KafkaProducer<String, String> producer = new KafkaProducer<>(props);
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, String.valueOf(message));
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {

                    exception.printStackTrace();

                    log.error("--==>" + exception.getMessage());
                } else {

                    log.info("Log message sent successfully to topic: {}", metadata.topic());

                    SuccessMsg.successMessage(String.format("Log message sent successfully to topic: " + metadata.topic()));
                }

            });

            producer.close();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to send to kafka broker {}", e.getLocalizedMessage());
            ErrorMsg.errorMessage("Failed to send to kafka broker!");
        }
    }
}
