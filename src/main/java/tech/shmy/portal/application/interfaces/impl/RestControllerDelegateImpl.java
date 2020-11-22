package tech.shmy.portal.application.interfaces.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Component;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.interfaces.IRestControllerDelegate;

import java.util.List;

@Component
public class RestControllerDelegateImpl<T> implements IRestControllerDelegate<T> {

    private IService<T> service;

    public IService<T> getService() {
        return service;
    }

    public void setService(IService<T> service) {
        this.service = service;
    }

    @Override
    public ResultBean<List<T>> list() {
        return ResultBean.success(service.list());
    }

    @Override
    public ResultBean<T> detail(String id) {
        return ResultBean.success(service.getById(id));
    }

    @Override
    public ResultBean<T> create(T data) {
        service.save(data);
        return ResultBean.success(data);
    }

    @Override
    public ResultBean<T> update(String id, T data) {
        service.updateById(data);
        return ResultBean.success(data);
    }

    @Override
    public ResultBean<T> delete(String id) {
        T record = service.getById(id);
        if (record != null) {
            service.removeById(id);
        }
        return ResultBean.success(null);
    }
}
