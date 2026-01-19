/* (C)2026 */
package dev.sb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticProperties {

  private String host;
  private String apiKey;

}
