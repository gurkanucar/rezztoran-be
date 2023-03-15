package com.rezztoran.rezztoranbe.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 * The type Bean config.
 */
@Configuration
public class BeanConfig {

  /**
   * B crypt password encoder b crypt password encoder.
   *
   * @return the b crypt password encoder
   */
@Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Model mapper model mapper.
   *
   * @return the model mapper
   */
@Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  /**
   * Gets free marker configuration.
   *
   * @return the free marker configuration
   */
@Bean
  public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
    FreeMarkerConfigurationFactoryBean fmConfigFactoryBean =
        new FreeMarkerConfigurationFactoryBean();
    fmConfigFactoryBean.setTemplateLoaderPath("/templates/");
    return fmConfigFactoryBean;
  }
}
