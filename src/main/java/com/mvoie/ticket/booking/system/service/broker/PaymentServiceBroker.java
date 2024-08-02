package com.mvoie.ticket.booking.system.service.broker;

import com.mvoie.ticket.booking.system.service.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:9091/payments")
public interface PaymentServiceBroker {

    @PostMapping
    BookingDTO makePayment(@RequestBody BookingDTO bookingDTO);
}
