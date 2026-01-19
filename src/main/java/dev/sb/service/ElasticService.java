package dev.sb.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ElasticService {

  private final ElasticsearchClient esClient;
  private final OllamaService ollamaService;

  public ElasticService(
    final ElasticsearchClient esClient,
    final OllamaService ollamaService
  ) {
    this.esClient = esClient;
    this.ollamaService = ollamaService;
    try {
      System.out.println("esClient: " + this.esClient.ping().value());
      // this.ingest();
      this.search("company name BBRI");
    } catch (ElasticsearchException | IOException e) {
      e.printStackTrace();
    }
  }

  public void ingest() throws ElasticsearchException, IOException {
    String text = Files.readString(
      Path.of("./docs/bank-rakyat-indonesia_overview.csv"),
      StandardCharsets.UTF_8
    );

    var vector = ollamaService.getEmbedding(text);
    esClient.index(i ->
      i
        .index("rag_docs")
        .document(
          Map.of(
            "content",
            text,
            "embedding",
            vector,
            "metadata",
            Map.of("source", "bank-rakyat-indonesia_overview.csv")
          )
        )
    );
  }

  public void search(String text) throws ElasticsearchException, IOException {
    float[] vector = ollamaService.getEmbedding(text);
    List<Float> queryVector = new ArrayList<>(vector.length);
    for (float v : vector) {
      queryVector.add(v);
    }

    var response = esClient.search(
      s ->
        s
          .index("rag_docs")
          .size(5)
          .query(q ->
            q.knn(k ->
              k
                .field("embedding")
                .queryVector(queryVector)
                .k(5)
                .numCandidates(100)
            )
          ),
      Map.class
    );

    response
      .hits()
      .hits()
      .forEach(hit -> {
        System.out.println("Content: " + hit.source().get("content"));
      });
  }
}
