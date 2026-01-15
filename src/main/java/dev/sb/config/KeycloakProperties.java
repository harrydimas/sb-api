/* (C)2026 */
package dev.sb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

  private String url;
  private String realm;
  private Admin admin = new Admin();
  private Client client = new Client();
  private Issuer issuer = new Issuer();

  @Data
  public static class Admin {

    private String username;
    private String password;
  }

  @Data
  public static class Client {

    private String id;
    private String secret;
  }

  @Data
  public static class Issuer {

    private String url;
  }
}
