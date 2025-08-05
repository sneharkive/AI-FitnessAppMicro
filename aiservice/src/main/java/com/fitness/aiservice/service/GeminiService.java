package com.fitness.aiservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeminiService {
  private final WebClient webClient;

  @Value("${spring.gemini.api.url}")
  private String geminiApiUrl;

  @Value("${spring.gemini.api.key}")
  private String geminiApiKey;

  public GeminiService(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.build();
  }

  public String getAnswer(String question){
    Map<String, Object> reqBody = Map.of(
      "contents", new Object[]{
        Map.of("parts", new Object[]{
          Map.of("text", question)
        })
      }
    );

    String response = webClient.post()
                      .uri(geminiApiUrl + geminiApiKey) 
                      .header("Content-Type", "application/json")
                      .bodyValue(reqBody).retrieve()
                      .bodyToMono(String.class)
                      .block();
    return response;
  }

}
