package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
//import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void getAvailablePlaceCount_test() {

//        since in line no 27 we are mocking roomserive thats why we are givinng values below. If we would have used actual class then we should have not required to pass values below
//        given
        List<Room> availableRooms = new ArrayList<>();
        availableRooms.add(new Room("1",1));
        availableRooms.add(new Room("2",2));
        availableRooms.add(new Room("3",3));
        when(roomServiceMock.getAvailableRooms()).thenReturn(availableRooms);
//        when
        int actualValue = bookingService.getAvailablePlaceCount();
        int expectedValue = 6;
//        then
        assertEquals(actualValue,expectedValue);
    }

    @Test
    void getAvailablePlaceCount_MultipleReturnValues_whenCalledMultipleTimes() {

//        since in line no 27 we are mocking roomserive thats why we are givinng values below. If we would have used actual class then we should have not required to pass values below
//        given
        List<Room> availableRooms = new ArrayList<>();
        availableRooms.add(new Room("1",1));
     List<Room> availableRoomOnSecondCall = new ArrayList<>();
        availableRoomOnSecondCall.add(new Room("2",2));
        availableRoomOnSecondCall.add(new Room("3",3));
        List<Room> availableRoomOnThirdCall = new ArrayList<>();
        availableRoomOnThirdCall.add(new Room("4",4));
        availableRoomOnThirdCall.add(new Room("5",5));
        when(roomServiceMock.getAvailableRooms()).thenReturn(availableRooms).thenReturn(availableRoomOnSecondCall).thenReturn(availableRoomOnThirdCall);
//        when
        int actualFirst = bookingService.getAvailablePlaceCount();
        int actualSecond = bookingService.getAvailablePlaceCount();
        int actualThird = bookingService.getAvailablePlaceCount();

//        then
        assertEquals(actualFirst,1);
        assertEquals(actualSecond,5);
        assertEquals(actualThird,9);
    }

    @Test
    void makeBooking_shouldThrowExceptionWhenNoRoomAvailable() {
//        given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01,01),LocalDate.of(2020,01,05),2,false);
        when(roomServiceMock.findAvailableRoomId(bookingRequest)).thenThrow(new BusinessException());

//        when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);
//        then
        assertThrows(BusinessException.class,executable);
    }
}