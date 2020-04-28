package cn.xiaoniu.cloud.server.api.controller;

import cn.xiaoniu.cloud.server.api.model.po.Directory;
import cn.xiaoniu.cloud.server.api.model.vo.DirectoryVO;
import cn.xiaoniu.cloud.server.service.DirectoryService;
import cn.xiaoniu.cloud.server.web.authority.Login;
import cn.xiaoniu.cloud.server.web.response.Result;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result<DirectoryVO> findDirectory(@RequestParam(name = "parentDirectoryId", required = false) Long parentDirectoryId) {
        return directoryService.findDirectory(parentDirectoryId);
    }

    /**
     * 新增文件夹
     *
     * @return Result
     * @author 孔明
     * @date
     */
    @Login
    @PostMapping("/createDirectory")
    @ApiOperation(value = "新增文件夹")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "AccessToken", value = "登录令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "parentId", value = "父级文件夹ID", paramType = "query"),
            @ApiImplicitParam(name = "directoryName", value = "文件夹名称(最大20个字符)", required = true, paramType = "query"),
    })
    public Result<DirectoryVO> createDirectory(@RequestParam(name = "parentId", defaultValue = "0") Long parentId,
                                               @RequestParam(name = "directoryName") String directoryName) {
        if (directoryName.length() > Directory.Constant.MAX_DIRECTORY_NAME_LENGTH) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "文件夹名称最大20个字符！");
        }
        return directoryService.createDirectory(parentId, directoryName);
    }

    /**
     * 修改文件/文件夹名称
     *
     * @return Result
     * @author 孔明
     * @date
     */
    @Login
    @PutMapping("/updateDirectoryName")
    @ApiOperation(value = "修改文件夹名称")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "AccessToken", value = "登录令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "directoryId", value = "文件夹ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "directoryName", value = "文件夹名称(最大20个字符)", required = true, paramType = "query"),
    })
    public Result<DirectoryVO> updateDirectoryName(@RequestParam(name = "directoryId") Long directoryId,
                                                   @RequestParam(name = "directoryName") String directoryName) {
        if (directoryName.length() > Directory.Constant.MAX_DIRECTORY_NAME_LENGTH) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "文件夹名称最大20个字符！");
        }
        return directoryService.updateDirectoryName(directoryId, directoryName);
    }

    /**
     * 删除文件夹/文件
     *
     * @param directoryId 目录ID
     * @return
     */
    @Login
    @DeleteMapping("/deleteDirectory")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "AccessToken", value = "登录令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "directoryId", value = "文件夹ID", required = true, paramType = "query")
    })
    public Result deleteDirectory(@RequestParam(name = "directoryId") Long directoryId) {
        return directoryService.deleteDirectory(directoryId);
    }

    /**
     * 移动目录
     *
     * @return Result
     * @author 孔明
     * @date
     */
    @Login
    @PutMapping("/moveDirectory")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "AccessToken", value = "登录令牌", required = true, paramType = "header"),
            @ApiImplicitParam(name = "sourceId", value = "源目录ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "targetId", value = "目标目录ID", required = true, paramType = "query")
    })
    public Result moveDirectory(@RequestParam("sourceId") Long sourceId, @RequestParam("targetId") Long targetId) {
        return directoryService.moveDirectory(sourceId, targetId);
    }
}
