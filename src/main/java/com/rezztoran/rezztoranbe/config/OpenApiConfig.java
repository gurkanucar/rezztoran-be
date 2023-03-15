package com.rezztoran.rezztoranbe.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Open api config.
 */
@Configuration
public class OpenApiConfig {

  /**
   * Custom open api open api.
   *
   * @param description the description
   * @param version the version
   * @return the open api
   */
@Bean
  public OpenAPI customOpenAPI(
      @Value("${application-description}") String description,
      @Value("${application-version}") String version) {
    final String securitySchemeName = "bearerAuth";
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(
            new Components()
                .addSecuritySchemes(
                    securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
        .info(
            new Info()
                .title("Rezztoran API")
                .version(version)
                .description(description)
                .license(new License().name("Rezztoran API Licence")));
  }
}
