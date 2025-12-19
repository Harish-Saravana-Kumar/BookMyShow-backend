package com.bookmyshow.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        logger.info("🚀 Configuring Cache Manager...");
        try {
            // Test Redis connection
            connectionFactory.getConnection().ping();
            logger.info("✅ Redis connected successfully!");
            return RedisCacheManager.create(connectionFactory);
        } catch (Exception e) {
            logger.warn("⚠️ Redis connection failed: {}. Using in-memory cache as fallback.", e.getMessage());
            // Fallback to in-memory cache if Redis is unavailable
            return new ConcurrentMapCacheManager("movies", "movie", "moviesByGenre", 
                    "showsByMovieId", "show", "availableSeats", "userBookings", "booking");
        }
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        logger.info("🚀 Configuring Redis Template...");
        
        try {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(connectionFactory);
            
            // Use String serialization for keys and values
            StringRedisSerializer stringSerializer = new StringRedisSerializer();
            template.setKeySerializer(stringSerializer);
            template.setValueSerializer(stringSerializer);
            
            // Hash operations serialization
            template.setHashKeySerializer(stringSerializer);
            template.setHashValueSerializer(stringSerializer);
            
            template.afterPropertiesSet();
            
            logger.info("✅ Redis Template configured successfully!");
            return template;
        } catch (Exception e) {
            logger.error("❌ Failed to configure Redis Template: {}", e.getMessage());
            throw new RuntimeException("Redis configuration failed", e);
        }
    }
}
