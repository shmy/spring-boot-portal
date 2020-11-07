package tech.shmy.portal.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultBean<T> {
    private int code;
    private String info;
    private T data;

    public static <V> ResultBean<V> error(String message) {
        return new ResultBean<>(200, message, null);
    }

    public static <V> ResultBean<V> success(V data) {
        return new ResultBean<>(0, "ok", data);
    }
}
