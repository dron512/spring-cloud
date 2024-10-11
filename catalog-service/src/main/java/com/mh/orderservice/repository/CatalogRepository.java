package com.mh.orderservice.repository;


import com.mh.orderservice.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<CatalogEntity,Integer> {

    CatalogEntity findByProductId(String productId);

}
