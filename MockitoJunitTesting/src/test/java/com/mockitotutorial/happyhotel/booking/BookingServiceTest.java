package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.*;
//import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup(){
        System.out.println("Before Each Method");
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);
        this.bookingService = new BookingService(paymentServiceMock,roomServiceMock,bookingDAOMock,mailSenderMock);
    }

    @Test
    public void calculatePrice(){

//        given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01,01),LocalDate.of(2020,01,05),2,false);
        double expected = 4 * 2 * 50.0;
//        when
        double actual = bookingService.calculatePrice(bookingRequest);
//        then
        assertEquals(expected,actual);
    }

}