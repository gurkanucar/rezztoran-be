package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.request.MailModel;
import com.rezztoran.rezztoranbe.model.Booking;
import com.rezztoran.rezztoranbe.model.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

  private final JavaMailSender emailSender;

  private final FreeMarkerConfigurer freeMarkerConfigurer;

  public void sendSimpleMessage(MailModel mailModel) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(mailModel.getTo());
    message.setSubject(mailModel.getSubject());
    message.setText(mailModel.getText());
    emailSender.send(message);
  }

  public void sendResetPasswordEmail(MailModel mailModel, User user) {
    try {

      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(mailModel.getTo());
      helper.setSubject("Reset Password Email Template");

      // Load the email template
      Configuration configuration = freeMarkerConfigurer.getConfiguration();
      Template emailTemplate = configuration.getTemplate("PasswordResetMail.html");

      // Prepare the model data
      Map<String, Object> model = new HashMap<>();
      model.put("username", user.getUsername());
      model.put("code", mailModel.getText());

      // Generate the email content using the template and the model
      String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(emailTemplate, model);
      helper.setText(emailContent, true);

      // Add inline image (if any)
      //    ClassPathResource imageResource = new ClassPathResource("path/to/image.png");
      //    helper.addInline("image.png", imageResource);

      // Add attachment (if any)
      //    ClassPathResource attachmentResource = new ClassPathResource("path/to/attachment.pdf");
      //    helper.addAttachment("attachment.pdf", attachmentResource);

      // Set the sender email address and send the email
      // helper.setFrom(mailProperties.getUsername());
      emailSender.send(message);
    } catch (Exception e) {
      log.error("error: ", e);
    }
  }

  public void sendBookCreatedMail(MailModel mailModel, Booking booking) {
    try {

      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(mailModel.getTo());
      helper.setSubject("Book created");

      // Load the email template
      Configuration configuration = freeMarkerConfigurer.getConfiguration();
      Template emailTemplate = configuration.getTemplate("BookCreatedMail.html");

      // Prepare the model data
      Map<String, Object> model = new HashMap<>();
      model.put("username", booking.getUser().getUsername());
      model.put(
          "booking_date",
          String.format("%s - %s", booking.getReservationDate(), booking.getReservationTime()));
      model.put("restaurant", booking.getRestaurant().getRestaurantName());
      model.put("note", booking.getNote());
      model.put("reservation_details_url", "http://localhost:8082/swagger-ui.html");

      // Generate the email content using the template and the model
      String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(emailTemplate, model);
      helper.setText(emailContent, true);

      // Add inline image (if any)
      ClassPathResource imageResource = new ClassPathResource("static/rezztoran_logo.png");
      helper.addInline("image", imageResource);

      // Add attachment (if any)
      //    ClassPathResource attachmentResource = new ClassPathResource("path/to/attachment.pdf");
      //    helper.addAttachment("attachment.pdf", attachmentResource);

      // Set the sender email address and send the email
      // helper.setFrom(mailProperties.getUsername());
      emailSender.send(message);
    } catch (Exception e) {
      log.error("error: ", e);
    }
  }
}
