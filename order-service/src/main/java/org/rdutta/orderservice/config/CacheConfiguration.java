package org.rdutta.orderservice.config;

import org.ehcache.Cache;
import org.ehcache.config.builders.*;
import org.ehcache.config.units.EntryUnit;
import org.rdutta.commonlibrary.util.OrderValidationStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    @Bean
    public Cache<String, OrderValidationStatus> validationCache() {
        org.ehcache.config.CacheConfiguration<String, OrderValidationStatus> cacheConfig =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class,
                                OrderValidationStatus.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(100, EntryUnit.ENTRIES)
                        ).withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(java.time.Duration.ofMinutes(30)))
                        .build();

        org.ehcache.CacheManager ehCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("orderStatusCache", cacheConfig)
                .build(true);

        return ehCacheManager.getCache("orderStatusCache", String.class, OrderValidationStatus.class);
    }
}