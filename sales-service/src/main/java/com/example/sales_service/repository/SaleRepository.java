package com.example.sales_service.repository;

import com.example.sales_service.model.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SaleRepository extends MongoRepository<Sale, String> {
}
