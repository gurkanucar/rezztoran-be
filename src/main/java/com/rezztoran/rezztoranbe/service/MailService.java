package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.dto.request.MailModel;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetMail;
import java.util.Map;

public interface MailService {

  void sendSimpleMessage(MailModel mailModel);
  /**
   * Send template email.
   *
   * @param mailModel the mail model
   * @param templateName the template name
   * @param model the model
   */
  void sendTemplateEmail(MailModel mailModel, String templateName, Map<String, Object> model);

  /**
   * Send reset password email.
   *
   * @param passwordResetMail the password reset mail
   */
  void sendResetPasswordEmail(PasswordResetMail passwordResetMail);

  /**
   * Send book created mail.
   *
   * @param mailModel the mail model
   * @param booking the booking
   */
  void sendBookCreatedMail(MailModel mailModel, BookDTO booking);
}
