package com.example.sales_service.repository;

import com.example.sales_service.model.Sale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SaleRepository extends MongoRepository<Sale, String> {

    List<Sale> findAllByOrderBySaleDateDesc(Pageable pageable);
}
