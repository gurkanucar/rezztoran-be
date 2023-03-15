package com.rezztoran.rezztoranbe.config;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetMail;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * The type Kafka producer config.
 */
@Configuration
public class KafkaProducerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.consumers.default-group-id}")
  private String groupId;

  /**
   * Booking producer factory producer factory.
   *
   * @return the producer factory
   */
@Bean("BookingKafkaProducerFactory")
  public ProducerFactory<String, BookDTO> bookingProducerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

    return new DefaultKafkaProducerFactory<>(configProps);
  }

  /**
   * Booking kafka template kafka template.
   *
   * @return the kafka template
   */
@Bean
  public KafkaTemplate<String, BookDTO> bookingKafkaTemplate() {
    return new KafkaTemplate<>(bookingProducerFactory());
  }

  /**
   * Password reset mail producer factory producer factory.
   *
   * @return the producer factory
   */
@Bean("PasswordResetMailKafkaProducerFactory")
  public ProducerFactory<String, PasswordResetMail> passwordResetMailProducerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  /**
   * Password reset mail kafka template kafka template.
   *
   * @return the kafka template
   */
@Bean
  public KafkaTemplate<String, PasswordResetMail> passwordResetMailKafkaTemplate() {
    return new KafkaTemplate<>(passwordResetMailProducerFactory());
  }
}
