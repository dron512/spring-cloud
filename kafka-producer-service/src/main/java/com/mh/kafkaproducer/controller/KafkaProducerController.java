package com.mh.kafkaproducer.controller;

import com.mh.kafkaproducer.dto.ProducerReqDto;
import com.mh.kafkaproducer.dto.ProducerResDto;
import com.mh.kafkaproducer.entity.ProducerEntity;
import com.mh.kafkaproducer.service.ProducerService;
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
public class KafkaProducerController {

    private final ProducerService producerService;
    private final Environment environment;
    private final ModelMapper modelMapper;

    @GetMapping("health-check")
    public String status() {
        return String.format("It's Working in Order Service on LOCAL PORT %s (SERVER PORT %s)",
                environment.getProperty("local.server.port"),
                environment.getProperty("server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ProducerResDto> createOrder(@PathVariable("userId") String userId,
                                                      @RequestBody ProducerReqDto producerReqDto) {
        log.info("Before add orders data");

        producerReqDto.setUserId(userId);
        ProducerResDto orderResDto = producerService.createOrder(producerReqDto);

        log.info("After added orders data");

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResDto);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ProducerResDto>> getAllOrders(@PathVariable("userId") String userId) {
        List<ProducerEntity> orderList = producerService.getOrdersByUserId(userId);

        List<ProducerResDto> result = orderList.stream()
                .map(producerEntity -> {
                    ProducerResDto orderResDto = modelMapper.map(producerEntity, ProducerResDto.class);
                    return orderResDto;
                })
                .toList();
        return ResponseEntity.ok(result);
    }
}
