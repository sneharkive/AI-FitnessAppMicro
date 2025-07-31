package com.fitness.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserResponse register(RegisterRequest req) {
    if (userRepository.existsByEmail(req.getEmail()))
      throw new RuntimeException("Email already exist");
    User user = new User();
    user.setEmail(req.getEmail());
    user.setPassword(req.getPassword());
    user.setFirstName(req.getFirstName());
    user.setLastName(req.getLastName());

    User savedUser = userRepository.save(user);
    return savedUser.toResponse();
  }

  public UserResponse getUserProfile(String userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
    return user.toResponse();
  }

  public Boolean existByUserId(String userId) {
    return userRepository.existsById(userId);
  }

}
