package tech.shmy.portal.application.domain;

public class ResultBean<T> {
    public Integer code;
    public String info;
    public T data;

    public ResultBean(Integer code, String info, T data) {
        this.code = code;
        this.info = info;
        this.data = data;
    }

    public static <V> ResultBean<V> error(String message) {
        return new ResultBean<>(200, message, null);
    }
    public static <V> ResultBean<V> success(V data) {
        return new ResultBean<>(0, "ok", data);
    }
}
