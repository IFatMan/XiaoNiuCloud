package com.xiao.niu.ServiceAPI;

import com.xiao.niu.bean.LoginBean;

import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Url;

/**
 * Created by 郑振楠 on 2017/6/27.
 */

public interface ServiceAPI {

    /**
     * 登录接口
     */
    /**
     * 登录接口
     */

    @PUT()
    Call<LoginBean> login(@Url String url);



}
