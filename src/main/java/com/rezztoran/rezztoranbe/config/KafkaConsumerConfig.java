package com.rezztoran.rezztoranbe.config;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetMail;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.consumers.default-group-id}")
  private String groupId;

  @Bean
  public ConsumerFactory<String, BookDTO> bookConsumerFactory() {
    Map<String, Object> props = setProps(bootstrapServers, groupId);
    return new DefaultKafkaConsumerFactory<>(
        props, new StringDeserializer(), new JsonDeserializer<>(BookDTO.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, BookDTO>
      bookingKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, BookDTO> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(bookConsumerFactory());
    // factory.setConcurrency(1);
    return factory;
  }

  @Bean
  public ConsumerFactory<String, PasswordResetMail> passwordResetConsumerFactory() {
    Map<String, Object> props = setProps(bootstrapServers, groupId);
    return new DefaultKafkaConsumerFactory<>(
        props, new StringDeserializer(), new JsonDeserializer<>(PasswordResetMail.class));
  }

  private static Map<String, Object> setProps(String bootstrapServers, String groupId) {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Allow deserialization of all packages
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    return props;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, PasswordResetMail>
      passwordResetKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, PasswordResetMail> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(passwordResetConsumerFactory());
    return factory;
  }
}
