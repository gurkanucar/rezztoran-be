package com.rezztoran.rezztoranbe.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * The type Async task executor.
 */
@Configuration
public class AsyncTaskExecutor {
  /**
   * Async executor executor.
   *
   * @return the executor
   */
@Bean
  public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(2);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("JDAsync-");
    executor.initialize();
    return executor;
  }
}
