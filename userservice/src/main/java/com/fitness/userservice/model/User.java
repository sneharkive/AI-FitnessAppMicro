package com.fitness.userservice.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fitness.userservice.dto.UserResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(unique = true, nullable = false)
  private String email;

  private String keycloakId;

  @Column(nullable = false)
  private String password;

  private String firstName;
  private String lastName;

  @Enumerated(EnumType.STRING)
  private UserRole role = UserRole.USER;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public UserResponse toResponse() {
    return new UserResponse(this.id, this.email, this.keycloakId, this.password, this.firstName, this.lastName, this.createdAt,
        this.updatedAt);
  }
}
