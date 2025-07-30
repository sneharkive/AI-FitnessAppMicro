package com.fitness.activityservice.service;

import java.util.ArrayList;
import java.util.List;

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

  public List<ActivityResponse> getUSerActivities(String userId) {
    List<Activity> activities = activityRepository.findByUserId(userId);

    return activities.stream()
        .map(Activity::toResponse)
        .toList();
  }

  public ActivityResponse getActivityById(String activityId) {
    return activityRepository.findById(activityId).orElseThrow(() -> new RuntimeException("Activity Not Found"))
        .toResponse();
  }

}
