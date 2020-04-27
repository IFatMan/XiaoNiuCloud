package cn.xiaoniu.cloud.server.api.controller;

import cn.xiaoniu.cloud.server.service.LoginService;
import cn.xiaoniu.cloud.server.web.authority.Login;
import cn.xiaoniu.cloud.server.web.log.HideData;
import cn.xiaoniu.cloud.server.web.response.Result;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import cn.xiaoniu.cloud.server.web.util.Log;
import io.swagger.annotations.Api;
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
@Api
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
    public Result login(@RequestParam("account") String account, @HideData @RequestParam("password") String password) {
        try {
            return loginService.login(account,password);
        }catch (Exception ex) {
            Log.error("登录接口异常:", ex);
            return Result.fail(ResultStatus.ERROR_SYSTEM,"系统异常！") ;
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
    public Result register(@RequestParam("name") String name, @RequestParam("account") String account,
                           @HideData @RequestParam("password") String password) {
        try {
            return loginService.register(name, account, password);
        }catch (Exception ex) {
            Log.error("注册接口异常：", ex);
            return Result.fail(ResultStatus.ERROR_SYSTEM,"系统异常！") ;
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
    public Result updatePwd(@HideData @RequestParam("oldPwd") String oldPwd, @HideData @RequestParam("password") String newPwd) {
        try {
            return loginService.updatePwd(oldPwd, newPwd);
        }catch (Exception ex) {
            Log.error("修改密码异常：", ex);
            return Result.fail(ResultStatus.ERROR_SYSTEM,"系统异常！") ;
        }
    }
}
