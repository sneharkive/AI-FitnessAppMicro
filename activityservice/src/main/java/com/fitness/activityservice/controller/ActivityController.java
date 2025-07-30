package com.fitness.activityservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@AllArgsConstructor
@RestController
@RequestMapping("/api/activities")
public class ActivityController {

  private ActivityService activityservice;

  @PostMapping
  public ResponseEntity<ActivityResponse> trackActivity( @RequestBody ActivityRequest req ) {      
    return ResponseEntity.ok(activityservice.trackActivity(req));
  }

  
  @GetMapping
  public ResponseEntity<List<ActivityResponse>> getUSerActivities( @RequestHeader("X-User-ID") String userId ) {      
    return ResponseEntity.ok(activityservice.getUSerActivities(userId));
  }

  
  @GetMapping("/{activityId}")
  public ResponseEntity<ActivityResponse> getActivity( @PathVariable String activityId ) {      
    return ResponseEntity.ok(activityservice.getActivityById(activityId));
  }
  
}
