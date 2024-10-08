package com.mh.catalogservice.repository;


import com.mh.catalogservice.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogRepository extends JpaRepository<CatalogEntity,Integer> {

    CatalogEntity findByProductId(String productId);

}
