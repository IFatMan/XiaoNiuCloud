package cn.xiaoniu.cloud.server.api.controller;

import cn.xiaoniu.cloud.server.service.DirectoryService;
import cn.xiaoniu.cloud.server.web.authority.Login;
import cn.xiaoniu.cloud.server.web.response.Result;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import cn.xiaoniu.cloud.server.web.util.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 孔明
 * @date 2020/4/26 11:38
 * @description cn.xiaoniu.cloud.server.api.controller.DirectoryController
 */
@Api(value = "文件夹操作接口", tags = "文件夹操作接口")
@RestController("/directory")
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    /**
     * 获取文件夹下文件及文件夹
     *
     * @param parentDirectoryId
     * @return
     */
    @Login
    @GetMapping("/findDirectory")
    @ApiOperation(value = "获取文件夹下文件及文件夹")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "AccessToken", value = "登录令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "parentDirectoryId", value = "父级文件夹ID", paramType = "query")
    })
    public Result findDirectory(@RequestParam(name = "parentDirectoryId", defaultValue = "0") Long parentDirectoryId) {
        try {
            return directoryService.findDirectory(parentDirectoryId);
        } catch (Exception ex) {
            Log.error("获取文件夹下文件及文件夹异常", ex);
            return Result.fail(ResultStatus.ERROR_SYSTEM, "系统异常！");
        }
    }
}
