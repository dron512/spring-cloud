package com.mh.catalogservice.controller;

import com.mh.catalogservice.dto.OrderReqDto;
import com.mh.catalogservice.dto.OrderResDto;
import com.mh.catalogservice.entity.OrderEntity;
import com.mh.catalogservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderResDto>> getAllOrders(@PathVariable("userId") String userId) {
        List<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<OrderResDto> result = orderList.stream()
                .map( orderEntity -> {
                    OrderResDto orderResDto = modelMapper.map(orderEntity, OrderResDto.class);
                    return orderResDto;
                })
                .toList();
        return ResponseEntity.ok(result);
    }
}
