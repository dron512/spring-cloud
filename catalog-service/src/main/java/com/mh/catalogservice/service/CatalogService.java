package com.mh.catalogservice.service;

import com.mh.catalogservice.dto.CatalogReqDto;
import com.mh.catalogservice.dto.CatalogResDto;
import com.mh.catalogservice.entity.CatalogEntity;
import com.mh.catalogservice.repository.CatalogRepository;
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
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public Iterable<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }

}