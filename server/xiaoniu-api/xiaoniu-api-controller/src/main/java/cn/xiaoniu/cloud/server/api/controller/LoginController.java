package cn.xiaoniu.cloud.server.api.controller;

import cn.xiaoniu.cloud.server.api.common.CacheUser;
import cn.xiaoniu.cloud.server.service.LoginService;
import cn.xiaoniu.cloud.server.web.authority.Login;
import cn.xiaoniu.cloud.server.web.log.HideData;
import cn.xiaoniu.cloud.server.web.response.Result;
import cn.xiaoniu.cloud.server.web.util.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 孔明
 * @date 2020/4/21 16:20
 * @description cn.xiaoniu.cloud.server.api.controller.LoginController
 */
@Api(value = "登陆" , tags = "登陆")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录接口
     *
     * @param account  账户
     * @param password 密码
     * @return
     */
    @PutMapping("/login")
    @ApiOperation(value = "登录接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "account", value = "账户", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "password", required = true, paramType = "query")
    })
    public Result<CacheUser> login(@RequestParam("account") String account, @HideData @RequestParam("password") String password) {
        try {
            return loginService.login(account,password);
        }catch (Exception ex) {
            Log.error("登录接口异常:", ex);
            return Result.errorSystem();
        }

    }

    /**
     * 注册接口
     *
     * @param name     用户名
     * @param account  账户
     * @param password 密码
     * @return
     */
    @PutMapping("/register")
    @ApiOperation(value = "注册接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name", value = "用戶名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "account", value = "账户", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "password", required = true, paramType = "query")
    })
    public Result register(@RequestParam("name") String name, @RequestParam("account") String account,
                           @HideData @RequestParam("password") String password) {
        try {
            return loginService.register(name, account, password);
        }catch (Exception ex) {
            Log.error("注册接口异常：", ex);
            return Result.errorSystem();
        }
    }


    /**
     * 修改密码
     *
     * @param oldPwd 原密码
     * @param newPwd 新密码
     * @return
     */
    @Login
    @PutMapping("/updatePwd")
    @ApiOperation(value = "修改密码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "AccessToken", value = "登录令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "oldPwd", value = "原密码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "newPwd", value = "新密码", required = true, paramType = "query"),
    })
    public Result updatePwd(@HideData @RequestParam("oldPwd") String oldPwd, @HideData @RequestParam("newPwd") String newPwd) {
        try {
            return loginService.updatePwd(oldPwd, newPwd);
        }catch (Exception ex) {
            Log.error("修改密码异常：", ex);
            return Result.errorSystem();
        }
    }
}
