package tech.shmy.portal.application.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.shmy.portal.application.domain.PostEntity;

@Service
public class PostService {
    private final String baseUrl = "http://localhost:3000";
    private final RestTemplate restTemplate;

    public PostService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public PostEntity[] list() {
        return this.restTemplate.getForObject(this.baseUrl + "/posts", PostEntity[].class);
    }
    public PostEntity detail(String id) {
        return this.restTemplate.getForObject(this.baseUrl + "/posts/" + id, PostEntity.class);
    }
}
