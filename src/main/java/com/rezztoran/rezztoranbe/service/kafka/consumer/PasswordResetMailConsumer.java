package com.rezztoran.rezztoranbe.service.kafka.consumer;

import com.rezztoran.rezztoranbe.dto.request.PasswordResetMail;

/**
 * The interface Password reset mail consumer.
 */
public interface PasswordResetMailConsumer {
  /**
   * Consume password reset mail.
   *
   * @param passwordResetMail the password reset mail
   */
void consumePasswordResetMail(PasswordResetMail passwordResetMail);
}
