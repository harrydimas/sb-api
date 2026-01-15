/* (C)2026 */
package dev.sb.service;

import dev.sb.domain.User;
import dev.sb.model.UserDTO;
import dev.sb.repos.UserRepository;
import dev.sb.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<UserDTO> findAll() {
    final List<User> users = userRepository.findAll(Sort.by("id"));
    return users.stream().map(user -> mapToDTO(user, new UserDTO())).toList();
  }

  public UserDTO get(final UUID id) {
    return userRepository
      .findById(id)
      .map(user -> mapToDTO(user, new UserDTO()))
      .orElseThrow(NotFoundException::new);
  }

  public UUID create(final UserDTO userDTO) {
    final User user = new User();
    mapToEntity(userDTO, user);
    return userRepository.save(user).getId();
  }

  public void update(final UUID id, final UserDTO userDTO) {
    final User user = userRepository
      .findById(id)
      .orElseThrow(NotFoundException::new);
    mapToEntity(userDTO, user);
    userRepository.save(user);
  }

  public void delete(final UUID id) {
    final User user = userRepository
      .findById(id)
      .orElseThrow(NotFoundException::new);
    userRepository.delete(user);
  }

  private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
    userDTO.setId(user.getId());
    userDTO.setName(user.getName());
    userDTO.setEmail(user.getEmail());
    userDTO.setKeycloakId(user.getKeycloakId());
    return userDTO;
  }

  private User mapToEntity(final UserDTO userDTO, final User user) {
    user.setName(userDTO.getName());
    user.setEmail(userDTO.getEmail());
    user.setKeycloakId(userDTO.getKeycloakId());
    return user;
  }

  public boolean emailExists(final String email) {
    return userRepository.existsByEmailIgnoreCase(email);
  }

  public boolean keycloakIdExists(final String keycloakId) {
    return userRepository.existsByKeycloakIdIgnoreCase(keycloakId);
  }
}
