package com.mh.catalogservice.service;

import com.mh.catalogservice.dto.OrderReqDto;
import com.mh.catalogservice.dto.OrderResDto;
import com.mh.catalogservice.entity.OrderEntity;
import com.mh.catalogservice.repository.OrderRepository;
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
public class OrderService  {
    private final OrderRepository orderRepository;

    private final Environment env;
    private final ModelMapper modelMapper;

    public OrderResDto createOrder(OrderReqDto orderReqDto) {
        orderReqDto.setOrderId(UUID.randomUUID().toString());
        orderReqDto.setTotalPrice(orderReqDto.getQty() * orderReqDto.getUnitPrice());

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = modelMapper.map(orderReqDto, OrderEntity.class);

        orderRepository.save(orderEntity);

        OrderResDto orderResDto = modelMapper.map(orderEntity, OrderResDto.class);

        return orderResDto;
    }

    public OrderResDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderResDto orderDto = new ModelMapper().map(orderEntity, OrderResDto.class);

        return orderDto;
    }

    public List<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }



}