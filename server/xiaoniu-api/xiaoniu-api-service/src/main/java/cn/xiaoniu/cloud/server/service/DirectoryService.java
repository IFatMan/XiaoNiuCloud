package cn.xiaoniu.cloud.server.service;

import cn.hutool.core.collection.CollUtil;
import cn.xiaoniu.cloud.server.api.common.CacheUser;
import cn.xiaoniu.cloud.server.api.dao.db.DirectoryAutoDao;
import cn.xiaoniu.cloud.server.api.model.po.Directory;
import cn.xiaoniu.cloud.server.api.model.vo.DirectoryVO;
import cn.xiaoniu.cloud.server.web.authority.AuthorityUtil;
import cn.xiaoniu.cloud.server.web.entity.EntityUtil;
import cn.xiaoniu.cloud.server.web.response.Result;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 孔明
 * @date 2020/4/27 10:29
 * @description cn.xiaoniu.cloud.server.service.DirectoryService
 */
@Service
public class DirectoryService {

    @Autowired
    private DirectoryAutoDao directoryAutoDao;

    /**
     * 获取文件夹下文件及文件夹
     *
     * @param parentDirectoryId
     * @return cn.xiaoniu.cloud.server.web.response.Result
     * @author 孔明
     * @date 2020-04-27 10:31:00
     */
    public Result findDirectory(Long parentDirectoryId) {
        CacheUser cacheUser = AuthorityUtil.getCurrCustomer();
        if (Objects.isNull(cacheUser)) {
            return Result.fail(ResultStatus.ERROR_OAUTH_NOT_LOGIN, "您还未登录");
        }
        Directory directory = EntityUtil.select(Directory.class);
        directory.setUserId(cacheUser.getId());
        directory.setParentId(parentDirectoryId);

        List<Directory> directories = directoryAutoDao.listByEntity(directory);
        if (CollUtil.isEmpty(directories)) {
            return Result.success();
        }

        List<DirectoryVO> vos = directories.stream().map(Directory::convertFromEntity).collect(Collectors.toList());
        return Result.success(vos);
    }
}
