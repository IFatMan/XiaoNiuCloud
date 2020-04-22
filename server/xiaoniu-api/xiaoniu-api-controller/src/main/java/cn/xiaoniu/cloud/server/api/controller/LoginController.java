package cn.xiaoniu.cloud.server.api.controller;

import cn.xiaoniu.cloud.server.web.annotation.HideData;
import cn.xiaoniu.cloud.server.web.log.PrintLog;
import cn.xiaoniu.cloud.server.web.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @PutMapping("/login")
    @ApiOperation(value = "登录接口")
    @PrintLog(description = "登录接口")
    public Result login(@RequestParam("account") String account, @HideData @RequestParam("password") String password) {
        return Result.success();
    }
}
