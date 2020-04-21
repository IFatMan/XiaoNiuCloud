package cn.xiaoniu.cloud.server.web.response;

import java.io.Serializable;

/**
 * 返回模型
 */
public class Result implements Serializable {

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
    private Object data;

    // ------------------------------------------------ 构造方法 ------------------------------------------------

    /**
     * 状态类型有，提示信息、数据为空
     *
     * @param type 状态类型
     */
    public Result(ResultStatus type) {
        this(type, null);
    }

    /**
     * 状态类型、提示信息有，数据为空
     *
     * @param type 状态类型
     * @param msg  提示信息
     */
    public Result(ResultStatus type, String msg) {
        this(type, msg, null);
    }

    /**
     * 状态类型、数据有，提示信息为空
     *
     * @param type 状态类型
     * @param data 数据
     */
    public Result(ResultStatus type, Object data) {
        this(type, null, data);
    }

    /**
     * 状态类型、提示信息、数据有
     *
     * @param type 状态类型
     * @param msg  提示信息
     * @param data 数据
     */
    public Result(ResultStatus type, String msg, Object data) {
        this(type.getCode(), msg, data);
    }

    /**
     * 状态类型、提示信息、数据有
     *
     * @param status 状态类型值
     * @param msg    提示信息
     * @param data   数据
     */
    protected Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    // ------------------------------------------------ 静态方法 ------------------------------------------------

    /**
     * 成功返回静态方法
     *
     * @param data 返回数据
     * @return
     */
    public static Result success(Object data) {
        return new Result(ResultStatus.SUCCESS, ResultStatus.SUCCESS.name(), data);
    }

    /**
     * 成功返回静态方法
     *
     * @return
     */
    public static Result success() {
        return new Result(ResultStatus.SUCCESS, ResultStatus.SUCCESS.name());
    }

    /**
     * 失败返回静态方法
     *
     * @param type 返回状态
     * @param msg  提示信息
     * @return
     */
    public static Result fail(ResultStatus type, String msg) {
        return new Result(type, msg);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
