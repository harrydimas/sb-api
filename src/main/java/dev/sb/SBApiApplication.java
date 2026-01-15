/* (C)2026 */
package dev.sb;

import dev.sb.config.KeycloakProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class SBApiApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SBApiApplication.class, args);
  }

  @Bean
  public RestClient keycloakRestClient() {
    return RestClient.builder().build();
  }

  @Bean
  public KeycloakProperties keycloakProperties() {
    return new KeycloakProperties();
  }
}
