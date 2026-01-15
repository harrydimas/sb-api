/* (C)2026 */
package dev.sb.service;

import dev.sb.config.KeycloakProperties;
import dev.sb.model.TokenResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class KeycloakService {

  private final RestClient restClient;
  private final KeycloakProperties props;

  private String getAdminToken() {
    String tokenUrl =
      props.getUrl() + "/realms/master/protocol/openid-connect/token";

    TokenResponse response = restClient
      .post()
      .uri(tokenUrl)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .body(
        "grant_type=password" +
        "&client_id=admin-cli" +
        "&username=" +
        props.getAdmin().getUsername() +
        "&password=" +
        props.getAdmin().getPassword()
      )
      .retrieve()
      .body(TokenResponse.class);

    return response.getAccessToken();
  }

  public String register(String email, String password) {
    String adminToken = this.getAdminToken();

    String usersUrl =
      props.getUrl() + "/admin/realms/" + props.getRealm() + "/users";

    Map<String, Object> payload = Map.of(
      "username",
      email,
      "email",
      email,
      "enabled",
      true,
      "emailVerified",
      true,
      "credentials",
      List.of(Map.of("type", "password", "value", password, "temporary", false))
    );

    ResponseEntity<Void> response = restClient
      .post()
      .uri(usersUrl)
      .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
      .contentType(MediaType.APPLICATION_JSON)
      .body(payload)
      .retrieve()
      .toEntity(Void.class);

    if (response.getStatusCode() != HttpStatus.CREATED) {
      throw new RuntimeException("Failed to register user");
    }

    String location = response.getHeaders().getFirst(HttpHeaders.LOCATION);
    if (location == null) {
      throw new RuntimeException("Location header not found");
    }

    return location.substring(location.lastIndexOf('/') + 1);
  }
}
