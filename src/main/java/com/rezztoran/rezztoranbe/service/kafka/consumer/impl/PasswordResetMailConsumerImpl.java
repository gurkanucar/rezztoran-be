package com.rezztoran.rezztoranbe.service.kafka.consumer.impl;

import com.rezztoran.rezztoranbe.dto.request.PasswordResetMail;
import com.rezztoran.rezztoranbe.service.MailService;
import com.rezztoran.rezztoranbe.service.kafka.consumer.PasswordResetMailConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PasswordResetMailConsumerImpl implements PasswordResetMailConsumer {

  private final MailService mailService;

  public PasswordResetMailConsumerImpl(MailService mailService) {
    this.mailService = mailService;
  }

  @KafkaListener(
      topics = "${spring.kafka.topics.password-reset}",
      containerFactory = "passwordResetKafkaListenerContainerFactory",
      groupId = "group-id")
  @Override
  public void consumePasswordResetMail(PasswordResetMail passwordResetMail) {
    mailService.sendResetPasswordEmail(passwordResetMail);
  }
}