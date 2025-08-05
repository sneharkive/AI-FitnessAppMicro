package com.fitness.activityservice.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

  private final ActivityRepository activityRepository;
  private final UserValidationService userValidationService;
  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.exchange.name}")
  private String exchange;

  @Value("${rabbitmq.routing.key}")
  private String routingKey;


  public ActivityResponse trackActivity(ActivityRequest req) {
    boolean isValidUser = userValidationService.validateUser(req.getUserId());

    if(!isValidUser) throw new RuntimeException("Invalid User : " + req.getUserId());

    Activity activity = Activity.builder()
        .userId(req.getUserId())
        .type(req.getType())
        .duration(req.getDuration())
        .caloriesBurned(req.getCaloriesBurned())
        .starTime(req.getStarTime())
        .additionalMetrics(req.getAdditionalMetrics())
        .build();

    Activity savedAct = activityRepository.save(activity);

    // Publish to RabbitMQ for AI Processing
    try{
        rabbitTemplate.convertAndSend(exchange, routingKey, savedAct);
    }catch(Exception e){
        log.error("Failed to publish activity to RabbitMQ : " , e);
    }

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
