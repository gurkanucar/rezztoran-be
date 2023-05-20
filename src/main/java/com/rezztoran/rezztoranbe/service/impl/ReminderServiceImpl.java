package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.model.Booking;
import com.rezztoran.rezztoranbe.service.BookService;
import com.rezztoran.rezztoranbe.service.ReminderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderServiceImpl implements ReminderService {

  private final BookService bookService;

  @Scheduled(fixedDelay = 120000) // 2 minutes
  public void sendReminderEmails() {
    log.info("send reminder emails");
    List<Booking> bookings = bookService.findBookingsWithReminderCondition();

    for (Booking booking : bookings) {
      bookService.sendBookReminderEvent(booking);
      bookService.setBookReminderMailStatus(booking.getId(), true);
    }
  }
}
