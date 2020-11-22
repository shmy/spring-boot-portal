package tech.shmy.portal.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    public void set(String key, Object val) {
        redisTemplate.opsForValue().set(key, val);
    }
    public void set(String key, Object val, long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, val, time, unit);
    }
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public void delete(String key) {
        redisTemplate.delete(key);
    }
    public void delete(List<String> keys) {
        redisTemplate.delete(keys);
    }
}
