package com.example.sales_service.service;

import com.example.sales_service.model.S3FileMetadata;
import com.example.sales_service.model.Sale;
import com.example.sales_service.repository.SaleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesService {

    private static final Logger logger = LoggerFactory.getLogger(SalesService.class);

    private final S3Service s3Service;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public SalesService(ProducerService producerService, S3Service s3Service, ObjectMapper objectMapper) {

        this.s3Service = s3Service;
    }

    public List<Sale> getRecentSales(int pageNumber, int limit) {
        Pageable pageable = PageRequest.of(pageNumber, limit);
        return saleRepository.findAllByOrderBySaleDateDesc(pageable);
    }

    public List<Sale> fetchSalesData() {
        try {
            List<Sale> sales = getRecentSales(0, 10);

            logger.info("5-Get Sales Data");
            return sales;
        } catch (Exception e) {
            logger.error("Error while fetching sales data", e);
            return null;
        }
    }

    public S3FileMetadata setSalesData() {
        List<Sale> sales = fetchSalesData();
        try {
            byte[] salesData = objectMapper.writeValueAsBytes(sales);

            String key = "sales.json";
            String contentType = "application/json";

            S3FileMetadata metadata = s3Service.uploadObject(salesData, key, contentType);

            logger.info("6-set-sales-data: {}", metadata);

            return metadata;
        } catch (Exception e) {
            logger.error("Error while set sales data", e);
        }
        return null;
    }
}
