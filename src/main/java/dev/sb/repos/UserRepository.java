/* (C)2026 */
package dev.sb.repos;

import dev.sb.domain.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
  boolean existsByEmailIgnoreCase(String email);

  boolean existsByKeycloakIdIgnoreCase(String keycloakId);
}
