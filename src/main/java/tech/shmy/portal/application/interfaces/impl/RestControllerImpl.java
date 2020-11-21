package tech.shmy.portal.application.interfaces.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.interfaces.IRestController;

import java.util.List;

public class RestControllerImpl<T> implements IRestController<T> {
    public IService<T> getService() {
        return null;
    }

    @Override
    @GetMapping("")
    public ResultBean<List<T>> list() {
        return ResultBean.success(getService().list());
    }

    @Override
    @GetMapping("/{id}")
    public ResultBean<T> detail(@PathVariable String id) {
        return ResultBean.success(getService().getById(id));
    }

    @Override
    @PostMapping("")
    public ResultBean<T> create(@RequestBody T data) {
        getService().save(data);
        return ResultBean.success(data);
    }

    @Override
    @PutMapping("{id}")
    public ResultBean<T> update(@PathVariable String id, @RequestBody T data) {
        getService().updateById(data);
        return ResultBean.success(data);
    }

    @Override
    @DeleteMapping("{id}")
    public ResultBean<T> delete(@PathVariable String id) {
        T record = getService().getById(id);
        if (record != null) {
            getService().removeById(id);
        }
        return ResultBean.success(null);
    }
}
