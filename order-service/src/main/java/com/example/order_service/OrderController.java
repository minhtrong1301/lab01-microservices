package com.example.order_service;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final RestClient restClient;

    // Khởi tạo RestClient để giao tiếp với Product Service
    public OrderController(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    // API lấy danh sách đơn hàng mẫu
    @GetMapping
    public List<Map<String, Object>> getOrders() {

        return List.of(
                Map.of("id", 101, "productId", 1, "quantity", 2),
                Map.of("id", 102, "productId", 2, "quantity", 1)
        );
    }

    // API tìm sản phẩm thông qua Order Service
    @GetMapping("/product/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable int id) {

        try {

            // Gọi API Product Service để lấy thông tin sản phẩm
            Map product = restClient.get()
                    .uri("/api/products/{id}", id)
                    .retrieve()
                    .body(Map.class);

            // Trả kết quả về cho Frontend
            return ResponseEntity.ok(product);

        } catch (HttpClientErrorException.NotFound e) {

            // Nếu Product Service trả về 404
            return ResponseEntity.notFound().build();

        }
    }
}
