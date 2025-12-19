package com.bookmyshow.controller;

import com.bookmyshow.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;

    @GetMapping
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Backend is running", "✅ OK"));
    }

    @GetMapping("/redis")
    public ResponseEntity<ApiResponse<String>> redisHealth() {
        try {
            if (redisConnectionFactory == null) {
                logger.warn("⚠️ Redis connection factory is null");
                return ResponseEntity.ok(new ApiResponse<>(false, "Redis not configured", null));
            }

            redisConnectionFactory.getConnection().ping();
            logger.info("✅ Redis connection successful!");
            return ResponseEntity.ok(new ApiResponse<>(true, "Redis connected successfully", "PONG"));
        } catch (Exception e) {
            logger.error("❌ Redis connection failed: {}", e.getMessage());
            return ResponseEntity.ok(new ApiResponse<>(false, "Redis connection failed: " + e.getMessage(), null));
        }
    }
}
