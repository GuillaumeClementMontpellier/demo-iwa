package fr.polytech.demo.util.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
class KafkaListenerExample {
    Logger LOG = LoggerFactory.getLogger(KafkaListenerExample.class);

//    @KafkaListener(topics = "my_topic")
//    void listener(String data) {
//        LOG.info(data);
//    }

    //    Recupere tous les messages stockés du topic lors du demarrage
    @KafkaListener(
            groupId = "my_topic",
            topicPartitions = @TopicPartition(
                    topic = "my_topic-1",
                    partitionOffsets = {@PartitionOffset(
                            partition = "0",
                            initialOffset = "0")}))
    void listenToPartitionWithOffset(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) int offset) {
        LOG.info("Received message [{}] from partition-{} with offset-{}",
                message,
                partition,
                offset);
    }

    @KafkaListener(topics = "my_topic", groupId = "my-topic")
    void listenToPartitionWithOffset2(
            ConsumerRecord<String,String> record,
            Acknowledgment acknowledgment) {
        LOG.info("Listener 2 - Received message [{}] from partition-{} with offset-{} recorded at: {}",
                record.value(),
                record.partition(),
                record.offset(),
                new Date(record.timestamp()));
        acknowledgment.acknowledge();
    }
}