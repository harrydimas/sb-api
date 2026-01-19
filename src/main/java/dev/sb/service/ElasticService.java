package dev.sb.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;

@Service
public class ElasticService {

  private final ElasticsearchClient esClient;

  public ElasticService(final ElasticsearchClient esClient) {
    this.esClient = esClient;
    try {
      System.out.println("esClient: " + this.esClient.ping().value());
    } catch (ElasticsearchException | IOException e) {
      e.printStackTrace();
    }
  }
}