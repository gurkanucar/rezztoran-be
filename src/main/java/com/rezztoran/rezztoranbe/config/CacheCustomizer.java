package com.rezztoran.rezztoranbe.config;

import com.rezztoran.rezztoranbe.constant.CacheProperties;
import java.util.List;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

/** The type Cache customizer. */
@Component
public class CacheCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

  private final CacheProperties cacheProperties;

  /**
   * Instantiates a new Cache customizer.
   *
   * @param cacheProperties the cache properties
   */
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
