package com.fitness.activityservice.service;

import org.springframework.stereotype.Service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService {

  private final ActivityRepository activityRepository;

  public ActivityResponse trackActivity(ActivityRequest req) {
    Activity activity = Activity.builder()
                        .userId(req.getUserId())
                        .type(req.getType())
                        .duration(req.getDuration())
                        .caloriesBurned(req.getCaloriesBurned())
                        .starTime(req.getStarTime())
                        .additionalMetrics(req.getAdditionalMetrics())
                        .build();

    Activity savedAct = activityRepository.save(activity);
    
    return savedAct.toResponse();
  }
  
}
