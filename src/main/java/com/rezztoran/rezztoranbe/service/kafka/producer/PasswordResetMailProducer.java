package com.rezztoran.rezztoranbe.service.kafka.producer;

import com.rezztoran.rezztoranbe.dto.request.PasswordResetMail;

/** The interface Password reset mail producer. */
public interface PasswordResetMailProducer {

  /**
   * Send password reset mail.
   *
   * @param passwordResetMail the password reset mail
   */
  void sendPasswordResetMail(PasswordResetMail passwordResetMail);
}
