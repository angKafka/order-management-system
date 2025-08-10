package org.rdutta.orderservice.config;

import org.ehcache.Cache;
import org.ehcache.config.builders.*;
import org.rdutta.commonlibrary.util.OrderValidationStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfiguration {

    @Bean
    public Cache<String, OrderValidationStatus> validationCache() {
        org.ehcache.config.CacheConfiguration<String, OrderValidationStatus> cacheConfig =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class,
                                OrderValidationStatus.class,
                                ResourcePoolsBuilder.heap(100)
                        ).withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(30)))
                        .build();

        org.ehcache.CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("orderStatusCache", cacheConfig)
                .build(true);

        return cacheManager.getCache("orderStatusCache", String.class, OrderValidationStatus.class);
    }
}