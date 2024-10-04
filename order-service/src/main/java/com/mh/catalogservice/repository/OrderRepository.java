package com.mh.catalogservice.repository;


import com.mh.catalogservice.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {

    OrderEntity findByOrderId(String orderId);
    List<OrderEntity> findByUserId(String userId);
}
