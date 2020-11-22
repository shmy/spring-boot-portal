package tech.shmy.portal.application.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.shmy.portal.application.domain.ResultBean;

import java.util.List;

public interface IRestController<T, ID> {
    JpaRepository<T, ID> getRepository();

    ResultBean<List<T>> list();

    ResultBean<T> detail(ID id) throws Exception;

    ResultBean<T> create(T data);

    ResultBean<T> update(ID id, T data) throws Exception;

    ResultBean<T> delete(ID id) throws Exception;
}
