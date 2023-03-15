package com.rezztoran.rezztoranbe.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type S 3 client configuration.
 */
@Configuration
public class S3ClientConfiguration {

  @Value("${aws.s3.endpoint-url}")
  private String endpointUrl;

  /**
   * Amazon s 3 amazon s 3.
   *
   * @return the amazon s 3
   */
@Bean
  AmazonS3 amazonS3() {
    AwsClientBuilder.EndpointConfiguration endpointConfiguration =
        new AwsClientBuilder.EndpointConfiguration(endpointUrl, Regions.US_EAST_1.getName());
    return AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(endpointConfiguration)
        .withPathStyleAccessEnabled(true)
        .build();
  }
}
