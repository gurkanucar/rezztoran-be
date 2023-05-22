package com.rezztoran.rezztoranbe.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** The type Cache properties. */
@Component
@ConfigurationProperties("cache-config")
@Getter
@Setter
public class CacheProperties {
  private String restaurantCacheName;
  private long restaurantCacheTtl;
  private String foodCacheName;
  private long foodCacheTtl;
  private String categoryCacheName;
  private long categoryCacheTtl;
}
