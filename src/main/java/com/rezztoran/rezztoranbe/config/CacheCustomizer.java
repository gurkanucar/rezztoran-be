package com.rezztoran.rezztoranbe.config;

import com.rezztoran.rezztoranbe.constant.CacheProperties;
import java.util.List;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

  private final CacheProperties cacheProperties;

  public CacheCustomizer(CacheProperties cacheProperties) {
    this.cacheProperties = cacheProperties;
  }

  @Override
  public void customize(ConcurrentMapCacheManager cacheManager) {
    cacheManager.setCacheNames(
        List.of(
            cacheProperties.getRestaurantCacheName(),
            cacheProperties.getFoodCacheName(),
            cacheProperties.getCategoryCacheName()));
    cacheManager.setAllowNullValues(false);
  }
}
