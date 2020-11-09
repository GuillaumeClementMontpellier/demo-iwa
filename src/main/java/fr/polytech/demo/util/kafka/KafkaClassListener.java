package fr.polytech.demo.util.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "class-level", topics = "my_topic")
class KafkaClassListener {
    Logger LOG = LoggerFactory.getLogger(KafkaListenerExample.class);

//    @KafkaHandler
//    void listen(String message) {
//        LOG.info("KafkaHandler[String] {}", message);
//    }
//
//    @KafkaHandler(isDefault = true)
//    void listenDefault(Object object) {
//        LOG.info("KafkaHandler[Default] {}", object);
//    }
}