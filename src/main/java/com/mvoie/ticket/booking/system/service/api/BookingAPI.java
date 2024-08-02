package com.mvoie.ticket.booking.system.service.api;

import com.mvoie.ticket.booking.system.service.dto.BookingDTO;
import com.mvoie.ticket.booking.system.service.dto.ResponseDTO;
import com.mvoie.ticket.booking.system.service.services.BookingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
@Validated
@Slf4j
public class BookingAPI {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createBooking(@Valid @RequestBody BookingDTO bookingDTO){
        log.info("Entered into Booking API with JSON request {} ", bookingDTO.toString());
        ResponseDTO responseDTO = this.bookingService.createBooking(bookingDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
