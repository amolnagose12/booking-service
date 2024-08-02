package com.mvoie.ticket.booking.system.service.services.Impl;

import com.mvoie.ticket.booking.system.service.broker.PaymentServiceBroker;
import com.mvoie.ticket.booking.system.service.dto.BookingDTO;
import com.mvoie.ticket.booking.system.service.dto.ResponseDTO;
import com.mvoie.ticket.booking.system.service.entity.BookingEntity;
import com.mvoie.ticket.booking.system.service.enums.BookingStatus;
import com.mvoie.ticket.booking.system.service.repository.BookingRepository;
import com.mvoie.ticket.booking.system.service.services.BookingService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentServiceBroker paymentServiceBroker;

    @Override
    @Transactional
    public ResponseDTO createBooking(BookingDTO bookingDTO) {
        log.info("Entered into BookingServiceImpl with request {} ", bookingDTO.toString());
        BookingEntity bookingEntity = BookingEntity.builder()
                .userId(bookingDTO.getUserId())
                .movieId(bookingDTO.getMovieId())
                .bookingAmount(bookingDTO.getBookingAmount())
                .bookingStatus(BookingStatus.PENDING)
                .seatsSelected(bookingDTO.getSeatsSelected())
                .showDate(bookingDTO.getShowDate())
                .showTime(bookingDTO.getShowTime())
                .build();
        this.bookingRepository.save(bookingEntity);
        bookingDTO.setBookingId(bookingEntity.getBookingId());

        log.info("calling stripe payment gateway to do payment for the amount {} with booking id {}", bookingEntity.getBookingAmount(), bookingEntity.getBookingId());
        BookingDTO paymentServiceResponse = this.paymentServiceBroker.makePayment(bookingDTO);

        return ResponseDTO.builder()
                .bookingDTO(BookingDTO.builder()
                .bookingId(bookingEntity.getBookingId())
                .userId(bookingEntity.getUserId())
                        .movieId(bookingEntity.getMovieId())
                .seatsSelected(bookingEntity.getSeatsSelected())
                .bookingStatus(bookingEntity.getBookingStatus())
                .bookingAmount(bookingEntity.getBookingAmount())
                .showDate(bookingEntity.getShowDate())
                .showTime(bookingEntity.getShowTime())
                .bookingStatus(paymentServiceResponse.getBookingStatus())
                .seatsSelected(bookingEntity.getSeatsSelected()).build())
                .build();
    }
}
