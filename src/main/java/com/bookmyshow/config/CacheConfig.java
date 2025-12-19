package com.bookmyshow.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);
    
    @Bean
    public CacheManager cacheManager() {
        logger.info("🚀 Initializing Caffeine Cache Manager...");
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
            "movies",
            "movie",
            "shows",
            "userBookings"
        );
        
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .recordStats());
        
        logger.info("✅ Caffeine Cache Manager Initialized Successfully!");
        logger.info("📊 Configured Caches: movies, movie, shows, userBookings");
        logger.info("⏱️ Cache TTL: 10 minutes | Max Size: 1000 entries");
        
        return cacheManager;
    }
}
