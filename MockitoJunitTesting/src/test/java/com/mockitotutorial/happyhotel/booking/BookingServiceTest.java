package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
class BookingServiceTest {

    @Mock
    PaymentService paymentService;

    @Mock
    RoomService roomService;

    private RoomService roomService3;

    @Mock
    BookingDAO bookingDAO;

    @Mock
    MailSender mailSender;

    @Spy
    private BookingService bookingService;

    @Test
    void testCaseForWelcomeMessage() {
        BookingService bookingService1 = new BookingService(paymentService,roomService,bookingDAO,mailSender);
        String expected = "Welcome Rahman";
        String actual = bookingService1.welcomeMessage("Rahman");
        assertEquals(expected,actual);
    }

    @Test
    void testCaseFor_getAvailablePlaceCount(){
        ArrayList room = new ArrayList<>();
        room.add(new Room("1",3));
        room.add(new Room("2",6));
        room.add(new Room("3",9));
        this.roomService3 = mock(RoomService.class);
        when(roomService3.getAvailableRooms()).thenReturn(room);
        BookingService bookingService1 = new BookingService(paymentService,roomService3,bookingDAO,mailSender);

        int actual = bookingService1.getAvailablePlaceCount();
        assertEquals(18, actual);
    }

    @Test
    void calculatePrice_testWithPrePaid(){
        BookingRequest bookingRequest = new BookingRequest("21",(LocalDate.parse("2007-12-05")),(LocalDate.parse("2007-12-08")),10,false);
        BookingService bookingService1 = new BookingService(paymentService,roomService,bookingDAO,mailSender);
        double actualValue = bookingService1.calculatePrice(bookingRequest);
        assertEquals(actualValue,1500.0);
    }


}