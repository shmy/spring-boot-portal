package tech.shmy.portal.application.controller;

import java.util.List;

public interface IRestController<T> {
    public List<T> list();

    public T detail(String id);

    public T create(T data);

    public T update(String id, T data);

    public T delete(String id);
}
