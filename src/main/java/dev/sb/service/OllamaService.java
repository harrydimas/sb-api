package dev.sb.service;

import dev.sb.model.OllamaEmbeddingResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class OllamaService {

  @Value("${ollama.host}")
  private String ollamaHost;

  @Value("${ollama.model}")
  private String ollamaModel;

  private final RestClient restClient;

  public float[] getEmbedding(String text) {
    Map<String, Object> payload = Map.of("model", ollamaModel, "prompt", text);

    OllamaEmbeddingResponse response = restClient
      .post()
      .uri(ollamaHost + "/api/embeddings")
      .body(payload)
      .retrieve()
      .body(OllamaEmbeddingResponse.class);

    return response.embedding();
  }
}
