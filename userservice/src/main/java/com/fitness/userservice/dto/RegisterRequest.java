package com.fitness.userservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid Email Format")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 6, message = "Password must have at least 6 characters")
  private String password;

  private String firstName;

  private String lastName;

  private String keycloakId;

}
