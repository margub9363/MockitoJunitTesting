package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.awt.print.Book;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    @Mock
    PaymentService paymentService;

    @Mock RoomService roomService;

    @Mock
    BookingDAO bookingDAO;

    @Mock
    MailSender mailSender;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void testCaseForWelcomeMessage() {
        BookingService bookingService1 = new BookingService(paymentService,roomService,bookingDAO,mailSender);
        String expected = "Welcome Rahman";
        String actual = bookingService1.welcomeMessage("Rahman");
        assertEquals(expected,actual);
    }
}