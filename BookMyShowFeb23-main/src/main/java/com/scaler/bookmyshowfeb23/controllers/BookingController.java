package com.scaler.bookmyshowfeb23.controllers;

import com.scaler.bookmyshowfeb23.dtos.BookMovieRequestDto;
import com.scaler.bookmyshowfeb23.dtos.BookMovieResponseDto;
import com.scaler.bookmyshowfeb23.dtos.ResponseStatus;
import com.scaler.bookmyshowfeb23.models.Booking;
import com.scaler.bookmyshowfeb23.services.BookingService;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;

    BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    public BookMovieResponseDto bookTicket(BookMovieRequestDto requestDto) {
        BookMovieResponseDto responseDto = new BookMovieResponseDto();
        try {
            Booking booking = bookingService.bookTicket(requestDto.getUserId(),
                    requestDto.getShowId(),
                    requestDto.getShowSeatIds());
            responseDto.setBookingId(booking.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
