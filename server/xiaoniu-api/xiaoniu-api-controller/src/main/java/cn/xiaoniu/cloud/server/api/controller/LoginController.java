package cn.xiaoniu.cloud.server.api.controller;

import cn.xiaoniu.cloud.server.util.context.CacheCustomer;
import cn.xiaoniu.cloud.server.web.authority.Permission;
import cn.xiaoniu.cloud.server.web.log.HideData;
import cn.xiaoniu.cloud.server.web.log.PrintLog;
import cn.xiaoniu.cloud.server.web.redis.RedisDataSourceHolder;
import cn.xiaoniu.cloud.server.web.redis.RedisSource;
import cn.xiaoniu.cloud.server.web.response.Result;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("登录接口")
@RestController
public class LoginController {

    @PrintLog("登录接口")
    @PutMapping("/login")
    @RedisSource("api")
    @ApiOperation("登录接口")
    public Result login(@RequestParam("account") String account, @HideData @RequestParam("password") String password) {
        CacheCustomer cacheCustomer = new CacheCustomer();
        List<String> permission = Lists.newArrayList("ABABC");
        cacheCustomer.setPermissions(permission);
        RedisDataSourceHolder.execute(redisUtil -> redisUtil.set("DDD", cacheCustomer));
        return Result.success();
    }

    @Permission("ABABC")
    @GetMapping("/testPermission")
    @ApiOperation("测试接口")
    public Result testPermission() {
        return Result.success();
    }

}
