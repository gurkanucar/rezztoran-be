package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.BookDTO;
import com.rezztoran.rezztoranbe.dto.request.MailModel;
import com.rezztoran.rezztoranbe.dto.request.PasswordResetMail;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/** The type Mail service. */
@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

  private final JavaMailSender emailSender;

  private final FreeMarkerConfigurer freeMarkerConfigurer;

  /**
   * Send simple message.
   *
   * @param mailModel the mail model
   */
  public void sendSimpleMessage(MailModel mailModel) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(mailModel.getTo());
    message.setSubject(mailModel.getSubject());
    message.setText(mailModel.getText());
    emailSender.send(message);
  }

  /**
   * Send template email.
   *
   * @param mailModel the mail model
   * @param templateName the template name
   * @param model the model
   */
  public void sendTemplateEmail(
      MailModel mailModel, String templateName, Map<String, Object> model) {
    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper =
          new MimeMessageHelper(
              message,
              MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
              StandardCharsets.UTF_8.name());

      helper.setTo(mailModel.getTo());
      helper.setSubject(mailModel.getSubject());

      // Load the email template
      Configuration configuration = freeMarkerConfigurer.getConfiguration();
      Template emailTemplate = configuration.getTemplate(templateName);

      // Generate the email content using the template and the model
      String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(emailTemplate, model);
      helper.setText(emailContent, true);

      // Add inline image (if any)
      ClassPathResource imageResource = new ClassPathResource("static/rezztoran_logo.png");
      helper.addInline("image", imageResource);

      emailSender.send(message);
    } catch (Exception e) {
      log.error("error: ", e);
    }
  }

  /**
   * Send reset password email.
   *
   * @param passwordResetMail the password reset mail
   */
  public void sendResetPasswordEmail(PasswordResetMail passwordResetMail) {
    Map<String, Object> model = new HashMap<>();
    model.put("username", passwordResetMail.getUsername());
    model.put("code", passwordResetMail.getMailModel().getText());

    sendTemplateEmail(passwordResetMail.getMailModel(), "PasswordResetMail.html", model);
  }

  /**
   * Send book created mail.
   *
   * @param mailModel the mail model
   * @param booking the booking
   */
  public void sendBookCreatedMail(MailModel mailModel, BookDTO booking) {
    Map<String, Object> model = new HashMap<>();
    model.put("username", booking.getUser().getUsername());
    model.put(
        "booking_date",
        String.format("%s - %s", booking.getReservationDate(), booking.getReservationTime()));
    model.put("restaurant", booking.getRestaurant().getRestaurantName());
    model.put("note", booking.getNote());
    model.put("reservation_details_url", "http://localhost:8082/swagger-ui.html");

    sendTemplateEmail(mailModel, "BookCreatedMail.html", model);
  }
}
