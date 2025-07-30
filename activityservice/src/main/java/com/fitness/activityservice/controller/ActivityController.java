package com.fitness.activityservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/api/activities")
public class ActivityController {

  private ActivityService activityservice;

  @PostMapping()
  public ResponseEntity<ActivityResponse> trackActivity( @RequestBody ActivityRequest req ) {      
    return ResponseEntity.ok(activityservice.trackActivity(req));
  }
  
}
