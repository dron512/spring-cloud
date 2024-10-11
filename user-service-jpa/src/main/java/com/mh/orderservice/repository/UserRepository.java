package com.mh.orderservice.repository;


import com.mh.orderservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    UserEntity findByEmail(String username);
    UserEntity findByUserId(String UserId);
}