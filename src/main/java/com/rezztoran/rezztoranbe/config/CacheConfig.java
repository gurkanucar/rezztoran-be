package com.rezztoran.rezztoranbe.config;

import com.rezztoran.rezztoranbe.constant.CacheProperties;
import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** The type Cache config. */
@Configuration
public class CacheConfig {

  private final CacheProperties cacheProperties;

  /**
   * Instantiates a new Cache config.
   *
   * @param cacheProperties the cache properties
   */
  public CacheConfig(CacheProperties cacheProperties) {
    this.cacheProperties = cacheProperties;
  }

  /**
   * Cache manager cache manager.
   *
   * @return the cache manager
   */
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
