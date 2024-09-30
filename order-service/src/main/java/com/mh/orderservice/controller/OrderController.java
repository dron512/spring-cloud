package com.mh.orderservice.controller;

import com.mh.orderservice.dto.OrderReqDto;
import com.mh.orderservice.dto.OrderResDto;
import com.mh.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("order-service")
public class OrderController {

    private final OrderService orderService;
    private final Environment environment;
    private final ModelMapper modelMapper;

    @GetMapping("health-check")
    public String status() {
        return String.format("It's Working in Order Service on LOCAL PORT %s (SERVER PORT %s)",
                environment.getProperty("local.server.port"),
                environment.getProperty("server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResDto> createOrder(@PathVariable("userId") String userId,
                                                   @RequestBody OrderReqDto orderReqDto) {
        log.info("Before add orders data");

        orderReqDto.setUserId(userId);
        OrderResDto orderResDto = orderService.createOrder(orderReqDto);

        log.info("After added orders data");

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResDto);
    }
}
