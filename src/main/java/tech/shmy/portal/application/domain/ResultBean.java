package tech.shmy.portal.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultBean<T> {
    private boolean ok;
    private String msg;
    private T data;

    public static <V> ResultBean<V> error(String message) {
        return new ResultBean<>(false, message, null);
    }

    public static <V> ResultBean<V> success(V data) {
        return new ResultBean<>(true, null, data);
    }
}
