package com.example.UserDetailsProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  @Bean(name = "api1WebClient")
  public WebClient api1WebClient() {
    return WebClient.builder()
        .baseUrl("https://api1.com")
        .clientConnector(new ReactorClientHttpConnector(HttpClient.create().responseTimeout(java.time.Duration.ofMillis(2000))))
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
        .build();
  }

  @Bean(name = "api2WebClient")
  public WebClient api2WebClient() {
    return WebClient.builder()
        .baseUrl("https://api2.com")
        .clientConnector(new ReactorClientHttpConnector(HttpClient.create().responseTimeout(java.time.Duration.ofMillis(1000))))
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
        .build();
  }

  @Bean(name = "api3WebClient")
  public WebClient api3WebClient() {
    return WebClient.builder()
        .baseUrl("https://api3.com")
        .clientConnector(new ReactorClientHttpConnector(HttpClient.create().responseTimeout(java.time.Duration.ofMillis(1000))))
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
        .build();
  }
}