package dev.sb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

@Configuration
public class ElasticConfig {

  @Bean
  public ElasticProperties elasticProperties() {
    return new ElasticProperties();
  }

  @Bean
  public ElasticsearchClient esClient(ElasticProperties elasticProperties) {
    ElasticsearchClient esClient = ElasticsearchClient.of(b -> b
        .host(elasticProperties.getHost())
        .apiKey(elasticProperties.getApiKey()));
    return esClient;
  }
}
