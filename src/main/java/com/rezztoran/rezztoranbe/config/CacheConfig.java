package com.rezztoran.rezztoranbe.config;

import com.rezztoran.rezztoranbe.constant.CacheProperties;
import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

  private final CacheProperties cacheProperties;

  public CacheConfig(CacheProperties cacheProperties) {
    this.cacheProperties = cacheProperties;
  }

  @Bean
  public CacheManager cacheManager() {
    ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
    cacheManager.setCacheNames(
        List.of(
            cacheProperties.getRestaurantCacheName(),
            cacheProperties.getFoodCacheName(),
            cacheProperties.getCategoryCacheName()));
    return cacheManager;
  }
}
