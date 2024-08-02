package com.mvoie.ticket.booking.system.service.services;

import com.mvoie.ticket.booking.system.service.dto.BookingDTO;
import com.mvoie.ticket.booking.system.service.dto.ResponseDTO;

public interface BookingService {
    ResponseDTO createBooking(BookingDTO bookingDTO);
}
