package fr.polytech.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {
    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("mytopic-1").partitions(0).replicas(1).build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("mytopic-2").partitions(0).replicas(1).config(TopicConfig.RETENTION_MS_CONFIG, "1680000").build();
    }
}