package cn.xiaoniu.cloud.server.api.controller;

import cn.xiaoniu.cloud.server.service.LoginService;
import cn.xiaoniu.cloud.server.util.context.CacheCustomer;
import cn.xiaoniu.cloud.server.util.id.IdUtil;
import cn.xiaoniu.cloud.server.web.authority.Login;
import cn.xiaoniu.cloud.server.web.authority.Permission;
import cn.xiaoniu.cloud.server.web.log.HideData;
import cn.xiaoniu.cloud.server.web.log.PrintLog;
import cn.xiaoniu.cloud.server.web.redis.RedisDataSourceHolder;
import cn.xiaoniu.cloud.server.web.redis.RedisSource;
import cn.xiaoniu.cloud.server.web.response.Result;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import cn.xiaoniu.cloud.server.web.util.Log;
import cn.xiaoniu.cloud.server.web.util.SpringUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @PutMapping("/login")
    @RedisSource("api")
    @ApiOperation(value = "登录接口")
    public Result login(@RequestParam("account") String account, @HideData @RequestParam("password") String password) {
        try {



            return loginService.login(account,password);
        }catch (Exception ex) {
            Log.error("打印Error信息！" , ex);
            return Result.fail(ResultStatus.ERROR_SYSTEM,"系统异常！") ;
        }

    }

    @GetMapping("/testPermission")
    @ApiOperation("测试接口")
    public Result testPermission() {
        return Result.success();
    }

    @ApiOperation("测试接口")
    @GetMapping("/testLoginPermission")
    public Result testLoginPermission() {
        return Result.success();
    }

    @PutMapping("/register")
    @ApiOperation(value = "注册接口")
    public Result register(@RequestParam("name") String name,@RequestParam("account") String account, @HideData @RequestParam("password") String password, @HideData @RequestParam("agapassword") String agapassword) {
        try {
            if(password.equals(agapassword)) {

            }
            return loginService.register(name, account,password,agapassword);
        }catch (Exception ex) {
            Log.error("打印Error信息！" , ex);
//            return Result.fail(ResultStatus.ERROR_SYSTEM,ex.getMessage()) ;
            return Result.fail(ResultStatus.ERROR_SYSTEM,"系统异常！") ;
        }
    }


    @Login
    @PutMapping("/updatePwd")
    @ApiOperation(value = "修改密码")
    public Result updatePwd(@HideData @RequestParam("oldPwd") String oldpsd, @HideData @RequestParam("password") String agapassword) {
        try {

            return loginService.updatePwd(oldpsd,agapassword);
        }catch (Exception ex) {
            Log.error("打印Error信息！" , ex);
            return Result.fail(ResultStatus.ERROR_SYSTEM,"系统异常！") ;
        }
    }
}
