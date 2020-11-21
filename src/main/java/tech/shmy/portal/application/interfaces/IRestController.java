package tech.shmy.portal.application.interfaces;

import tech.shmy.portal.application.domain.ResultBean;

import java.util.List;

public interface IRestController<T> {
    ResultBean<List<T>> list();

    ResultBean<T> detail(String id);

    ResultBean<T> create(T data);

    ResultBean<T> update(String id, T data);

    ResultBean<T> delete(String id);
}
