package tech.shmy.portal.application.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.shmy.portal.application.domain.Post;

@Service
public class PostService {
    private final String baseUrl = "http://localhost:3000";
    private final RestTemplate restTemplate;

    public PostService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Post[] list() {
        return this.restTemplate.getForObject(this.baseUrl + "/posts", Post[].class);
    }
    public Post detail(String id) {
        return this.restTemplate.getForObject(this.baseUrl + "/posts/" + id, Post.class);
    }
}
