package com.mh.kafkaproducer.service;

import com.mh.kafkaproducer.dto.ProducerReqDto;
import com.mh.kafkaproducer.dto.ProducerResDto;
import com.mh.kafkaproducer.entity.ProducerEntity;
import com.mh.kafkaproducer.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerService  {
    private final ProducerRepository producerRepository;

    private final Environment env;
    private final ModelMapper modelMapper;

    public ProducerResDto createOrder(ProducerReqDto producerReqDto) {
        producerReqDto.setOrderId(UUID.randomUUID().toString());
        producerReqDto.setTotalPrice(producerReqDto.getQty() * producerReqDto.getUnitPrice());

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProducerEntity producerEntity = modelMapper.map(producerReqDto, ProducerEntity.class);

        producerRepository.save(producerEntity);

        ProducerResDto orderResDto = modelMapper.map(producerEntity, ProducerResDto.class);

        return orderResDto;
    }

    public ProducerResDto getOrderByOrderId(String orderId) {
        ProducerEntity producerEntity = producerRepository.findByOrderId(orderId);
        ProducerResDto orderDto = new ModelMapper().map(producerEntity, ProducerResDto.class);

        return orderDto;
    }

    public List<ProducerEntity> getOrdersByUserId(String userId) {
        return producerRepository.findByUserId(userId);
    }



}