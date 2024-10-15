package com.mh.userservice.feignclient;

import com.mh.userservice.vo.ResponseCatalog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="CATALOG-SERVICE")
public interface CatalogServiceClient {

    @GetMapping("/catalogs")
    List<ResponseCatalog> getCatalogs();

    @GetMapping("/")
    String test();
}