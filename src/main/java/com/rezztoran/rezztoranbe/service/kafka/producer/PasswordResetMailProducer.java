package com.rezztoran.rezztoranbe.service.kafka.producer;

import com.rezztoran.rezztoranbe.dto.request.PasswordResetMail;

public interface PasswordResetMailProducer {

  void sendPasswordResetMail(PasswordResetMail passwordResetMail);
}
