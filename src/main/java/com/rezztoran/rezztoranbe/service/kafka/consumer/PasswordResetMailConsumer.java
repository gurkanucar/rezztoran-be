package com.rezztoran.rezztoranbe.service.kafka.consumer;

import com.rezztoran.rezztoranbe.dto.request.PasswordResetMail;

public interface PasswordResetMailConsumer {
  void consumePasswordResetMail(PasswordResetMail passwordResetMail);
}
