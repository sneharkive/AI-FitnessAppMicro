# Connect to Eureka server

- made changes in pom.xml and application.yml

# Now need to have a connection between activity-service and user-service

### User must be present in user-service database to have activity in activity-service

- Add dependency in pom.xml in activity-service

```xml
		 <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
```

- Add WebClientConfig.java in activity-service

```java
@Configuration
public class WebClientConfig {

  @Bean
  @LoadBalanced
  public WebClient.Builder webClientBuilder (){
    return WebClient.builder();
  }

  @Bean
  public WebClient userServiceWebClient(WebClient.Builder webClientBuilder){
    return webClientBuilder
            .baseUrl("http://USER-SERVICE")
            .build();
  }
}
```

- Add endpoint in user-service

```java
  @GetMapping("/{userId}/validate")
  public ResponseEntity<Boolean> validateUser(@PathVariable String userId) {
    return ResponseEntity.ok(userService.existByUserId(userId));
  }
```

- Add UserValidationService.java in activity-service

```java
@Service
@RequiredArgsConstructor
public class UserValidationService {
  private final WebClient userServiceWebClient;

  public boolean validateUser(String userId) {
    try {
      return userServiceWebClient.get()
          .uri("/api/users/{userId}/validate", userId)
          .retrieve().bodyToMono(Boolean.class).block();
    } catch (WebClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND)
        throw new RuntimeException("User Not Found");
      else if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
        throw new RuntimeException("Invalid Request");
      throw new RuntimeException("Unexpected Error", e);
    }
  }

}
```

- Now before create new Activity we need to check the user is valid or not

```java
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

    return savedAct.toResponse();
  }
```
