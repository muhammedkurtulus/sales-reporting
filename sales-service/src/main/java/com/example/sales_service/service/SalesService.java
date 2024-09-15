package com.example.sales_service.service;

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

    private final ProducerService producerService;

    @Autowired
    private SaleRepository saleRepository;

    public SalesService(ProducerService producerService) {
        this.producerService = producerService;
    }

    public List<Sale> getRecentSales(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return saleRepository.findAllByOrderBySaleDateDesc(pageable);
    }

    public List<Sale> fetchSalesData() {
        try {
            List<Sale> sales = getRecentSales(10);

            logger.info(sales.getFirst().toString());
            logger.info("5-Get Sales Data");
            return sales;
        } catch (Exception e) {
            logger.error("Error while fetching sales data", e);
            return null;
        }
    }

    public void returnSalesData() {
        List<Sale> sales = fetchSalesData();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String salesJson = objectMapper.writeValueAsString(sales);

            producerService.sendMessage("sales-data-response", salesJson);
            logger.info("6-sales-data-response: {}", salesJson);
        } catch (Exception e) {
            logger.error("Error while return sales data", e);
        }
    }
}
