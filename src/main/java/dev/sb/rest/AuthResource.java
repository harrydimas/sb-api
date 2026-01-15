/* (C)2026 */
package dev.sb.rest;

import dev.sb.model.TokenResponse;
import dev.sb.service.KeycloakService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthResource {

  private final KeycloakService service;

  @PostMapping("/register")
  public ResponseEntity<?> register(
    @RequestParam String email,
    @RequestParam String password
  ) {
    String keycloakUserId = service.register(email, password);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        Map.of(
          "keycloakUserId",
          keycloakUserId,
          "message",
          "User registered successfully"
        )
      );
  }

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(
    @RequestParam String email,
    @RequestParam String password
  ) {
    return ResponseEntity.ok(service.login(email, password));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<TokenResponse> refreshToken(
    @RequestParam String refreshToken
  ) {
    return ResponseEntity.ok(service.refreshToken(refreshToken));
  }
}
