package tech.shmy.portal.application.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.application.domain.ResultBean;

import java.util.List;

@RestController
public class RestControllerImpl<T, S extends IService<T>> implements IRestController<T> {
    @Autowired
    public S service;

    @Override
    @GetMapping("")
    public ResultBean<List<T>> list() {
        return ResultBean.success(service.list());
    }

    @Override
    @GetMapping("{id}")
    public ResultBean<T> detail(@PathVariable String id) {
        return ResultBean.success(service.getById(id));
    }

    @Override
    @PostMapping("")
    public ResultBean<T> create(@RequestBody T data) {
        service.save(data);
        return ResultBean.success(data);
    }

    @Override
    @PutMapping("{id}")
    public ResultBean<T> update(@PathVariable String id, @RequestBody T data) {
        service.updateById(data);
        return ResultBean.success(data);
    }

    @Override
    @DeleteMapping("{id}")
    public ResultBean<T> delete(@PathVariable String id) {
        T record = service.getById(id);
        if (record != null) {
            service.removeById(id);
        }
        return ResultBean.success(null);
    }
}
