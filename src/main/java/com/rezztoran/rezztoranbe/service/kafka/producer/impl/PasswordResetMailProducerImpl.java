package com.rezztoran.rezztoranbe.service.kafka.producer.impl;

import com.rezztoran.rezztoranbe.dto.request.PasswordResetMail;
import com.rezztoran.rezztoranbe.service.kafka.producer.PasswordResetMailProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * The type Password reset mail producer.
 */
@Service
@Slf4j
public class PasswordResetMailProducerImpl implements PasswordResetMailProducer {
  @Qualifier("PasswordResetMailKafkaProducerFactory")
  private final KafkaTemplate<String, PasswordResetMail> kafkaTemplate;

  /**
   * The Topic name.
   */
@Value("${spring.kafka.topics.password-reset}")
  String topicName;

  /**
   * Instantiates a new Password reset mail producer.
   *
   * @param kafkaTemplate the kafka template
   */
public PasswordResetMailProducerImpl(KafkaTemplate<String, PasswordResetMail> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void sendPasswordResetMail(PasswordResetMail passwordResetMail) {
    kafkaTemplate.send(topicName, passwordResetMail);
  }
}
