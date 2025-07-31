package com.fitness.activityservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {


  // * `@LoadBalanced`: It signifies that the `WebClient.Builder` instance created
  // by this method should be configured to work with a client-side load balancer.
  // When you use this `WebClient.Builder` to create a `WebClient` (as shown in
  // the next method), any requests made to a service registered with a discovery
  // server (like "http://USER-SERVICE" in this example) will automatically be
  // load-balanced across multiple instances of that service. Without
  // `@LoadBalanced`, you would need to specify the exact IP address and port of
  // each service instance.
  @Bean
  @LoadBalanced
  public WebClient.Builder webClientBuilder() {
    return WebClient.builder();
  }

  @Bean
  public WebClient userServiceWebClient(WebClient.Builder webClientBuilder) {
    return webClientBuilder
        .baseUrl("http://USER-SERVICE")
        .build();

    // This configures the base URL for this specific `WebClient` instance. When you
    // make a request using `userServiceWebClient`, you only need to provide the
    // path relative to "http://USER-SERVICE". The "USER-SERVICE" part here is a
    // logical service ID, which will be resolved to actual service instances by the
    // load balancer (thanks to `@LoadBalanced` on the builder).
  }
}
