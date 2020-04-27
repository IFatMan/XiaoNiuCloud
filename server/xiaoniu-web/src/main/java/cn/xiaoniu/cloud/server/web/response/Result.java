package cn.xiaoniu.cloud.server.web.response;

import java.io.Serializable;
import java.util.Collection;

/**
 * 返回模型
 */
public class Result<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -8538212854484724851L;
    /**
     * 状态类型
     */
    private Integer status;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 多条数据返回
     */
    private Collection<T> collection;

    // ------------------------------------------------ 构造方法 ------------------------------------------------

    /**
     * 状态类型有，提示信息、数据为空
     *
     * @param type 状态类型
     */
    public Result(ResultStatus type) {
        this(type, (T) null);
    }

    /**
     * 状态类型、提示信息有，数据为空
     *
     * @param type 状态类型
     * @param msg  提示信息
     */
    public Result(ResultStatus type, String msg) {
        this(type.getCode(), msg, null, null);
    }

    /**
     * 状态类型、数据有，提示信息为空
     *
     * @param type 状态类型
     * @param data 数据
     */
    public Result(ResultStatus type, T data) {
        this(type, null, data);
    }

    /**
     * 状态类型、提示信息、数据有
     *
     * @param type 状态类型
     * @param msg  提示信息
     * @param data 数据
     */
    public Result(ResultStatus type, String msg, T data) {
        this(type.getCode(), msg, data, null);
    }

    public Result(ResultStatus type, String msg, Collection<T> collection) {
        this(type.getCode(), msg, null, collection);
    }

    /**
     * 状态类型、提示信息、数据有
     *
     * @param status 状态类型值
     * @param msg    提示信息
     * @param data   数据
     */
    protected Result(Integer status, String msg, T data, Collection<T> collection) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.collection = collection;
    }

    // ------------------------------------------------ 静态方法 ------------------------------------------------

    /**
     * 成功返回静态方法
     *
     * @param data 返回数据
     * @return
     */
    public static <T extends Serializable> Result<T> success(T data) {
        return new Result<>(ResultStatus.SUCCESS, ResultStatus.SUCCESS.name(), data);
    }

    /**
     * 成功返回静态方法
     *
     * @return
     */
    public static <T extends Serializable> Result<T> success() {
        return new Result<>(ResultStatus.SUCCESS, ResultStatus.SUCCESS.name());
    }

    /**
     * 成功返回静态方法
     *
     * @return
     */
    public static <T extends Serializable> Result<T> success(Collection<T> collection) {
        return new Result<>(ResultStatus.SUCCESS, ResultStatus.SUCCESS.name(), collection);
    }

    /**
     * 失败返回静态方法
     *
     * @param type 返回状态
     * @param msg  提示信息
     * @return
     */
    public static <T extends Serializable> Result<T> fail(ResultStatus type, String msg) {
        return new Result<>(type, msg);
    }

    /**
     * 新建系统异常返回
     *
     * @return
     */
    public static <T extends Serializable> Result<T> errorSystem() {
        return new Result<>(ResultStatus.ERROR_SYSTEM, "系统异常！");
    }

    // ------------------------------------------------ Getter/Setter方法 ------------------------------------------------
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Collection<T> getCollection() {
        return collection;
    }

    public void setCollection(Collection<T> collection) {
        this.collection = collection;
    }
}
