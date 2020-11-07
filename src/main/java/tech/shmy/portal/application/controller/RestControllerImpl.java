package tech.shmy.portal.application.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestControllerImpl<T, S extends IService<T>> implements IRestController<T> {
    @Autowired
    public S service;

    @Override
    @GetMapping("")
    public List<T> list() {
        return service.list();
    }

    @Override
    @GetMapping("{id}")
    public T detail(@PathVariable String id) {
        return service.getById(id);
    }

    @Override
    @PostMapping("")
    public T create(@RequestBody T data) {
        service.save(data);
        return data;
    }

    @Override
    @PutMapping("{id}")
    public T update(@PathVariable String id, @RequestBody T data) {
        return null;
    }

    @Override
    @DeleteMapping("{id}")
    public T delete(@PathVariable String id) {
        T record = service.getById(id);
        if (record != null) {
            service.removeById(id);
        }
        return null;
    }
}
