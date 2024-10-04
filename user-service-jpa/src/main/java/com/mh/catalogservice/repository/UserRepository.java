package com.mh.catalogservice.repository;


import com.mh.catalogservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    UserEntity findByEmail(String username);
}
