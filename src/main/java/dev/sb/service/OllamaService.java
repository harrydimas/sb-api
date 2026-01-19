package dev.sb.service;

import dev.sb.model.OllamaEmbeddingResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class OllamaService {

  private final RestClient restClient;

  public float[] getEmbedding(String text) {
    Map<String, Object> payload = Map.of("model", "llama3.1", "prompt", text);

    OllamaEmbeddingResponse response = restClient
      .post()
      .uri("http://localhost:11434/api/embeddings")
      .body(payload)
      .retrieve()
      .body(OllamaEmbeddingResponse.class);

    return response.embedding();
  }
}
