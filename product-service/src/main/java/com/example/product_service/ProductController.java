
package com.example.product_service;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

// Cho phép Frontend từ các Origin khác nhau gọi API
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // API lấy danh sách tất cả sản phẩm
    @GetMapping
    public List<Map<String, Object>> getProducts() {

        return List.of(
                Map.of("id", 1, "name", "Mouse", "price", 150000),
                Map.of("id", 2, "name", "Keyboard", "price", 250000),
                Map.of("id", 3, "name", "Monitor", "price", 2500000)
        );
    }

    // API tìm kiếm sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(
            @PathVariable int id) {

        // Danh sách sản phẩm mẫu
        List<Map<String, Object>> products = List.of(
                Map.of("id", 1, "name", "Mouse", "price", 150000),
                Map.of("id", 2, "name", "Keyboard", "price", 250000),
                Map.of("id", 3, "name", "Monitor", "price", 2500000)
        );

        // Tìm sản phẩm có ID trùng với ID được truyền vào
        return products.stream()
                .filter(product -> product.get("id").equals(id))
                .findFirst()

                // Nếu tìm thấy thì trả về HTTP 200
                .map(ResponseEntity::ok)

                // Nếu không tìm thấy thì trả về HTTP 404
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
