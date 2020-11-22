package tech.shmy.portal.application.interfaces.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.entity.IEntity;
import tech.shmy.portal.application.interfaces.IRestController;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Component
public class RestControllerImpl<T extends IEntity<ID>, ID extends Serializable> implements IRestController<T, ID> {

    @Override
    public JpaRepository<T, ID> getRepository() {
        return null;
    }

    @Override
    public ResultBean<List<T>> list() {
        return ResultBean.success(getRepository().findAll());
    }

    @Override
    public ResultBean<T> detail(ID id) throws Exception {
        Optional<T> optionalT = getRepository().findById(id);
        if (!optionalT.isPresent()) {
            throw new Exception("common.resource.not_exist");
        }
        return ResultBean.success(optionalT.get());
    }

    @Override
    public ResultBean<T> create(T data) {
        getRepository().save(data);
        return ResultBean.success(data);
    }

    @Override
    public ResultBean<T> update(ID id, T data) throws Exception {
        Optional<T> optionalT = getRepository().findById(id);
        if (!optionalT.isPresent()) {
            throw new Exception("common.resource.not_exist");
        }
        data.setId(id);
        getRepository().save(data);
        return ResultBean.success(data);
    }

    @Override
    public ResultBean<T> delete(ID id) throws Exception {
        Optional<T> optionalT = getRepository().findById(id);
        if (!optionalT.isPresent()) {
            throw new Exception("common.resource.not_exist");
        }
        getRepository().deleteById(id);
        return ResultBean.success(null);
    }
}
