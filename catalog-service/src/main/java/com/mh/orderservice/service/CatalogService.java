package com.mh.orderservice.service;

import com.mh.orderservice.entity.CatalogEntity;
import com.mh.orderservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public Iterable<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }

}