package cn.xiaoniu.cloud.server.service;

import cn.hutool.core.collection.CollUtil;
import cn.xiaoniu.cloud.server.api.common.CacheUser;
import cn.xiaoniu.cloud.server.api.dao.db.DirectoryAutoDao;
import cn.xiaoniu.cloud.server.api.dao.db.DirectoryDao;
import cn.xiaoniu.cloud.server.api.model.po.Directory;
import cn.xiaoniu.cloud.server.api.model.vo.DirectoryVO;
import cn.xiaoniu.cloud.server.util.DateUtil;
import cn.xiaoniu.cloud.server.util.id.IdUtil;
import cn.xiaoniu.cloud.server.web.authority.AuthorityUtil;
import cn.xiaoniu.cloud.server.web.entity.EntityUtil;
import cn.xiaoniu.cloud.server.web.exception.ThrowsException;
import cn.xiaoniu.cloud.server.web.response.Result;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private DirectoryDao directoryDao;

    /**
     * 获取文件夹下文件及文件夹
     *
     * @param parentDirectoryId
     * @return cn.xiaoniu.cloud.server.web.response.Result
     * @author 孔明
     * @date 2020-04-27 10:31:00
     */
    public Result<DirectoryVO> findDirectory(Long parentDirectoryId) {
        CacheUser cacheUser = AuthorityUtil.getCurrCustomer();
        if (Objects.isNull(parentDirectoryId)) {
            parentDirectoryId = cacheUser.getRootDirectoryId();
        }

        Directory directory = EntityUtil.select(Directory.class);
        directory.setUserId(cacheUser.getId());
        directory.setParentId(parentDirectoryId);

        List<Directory> directories = directoryAutoDao.listByEntity(directory);
        if (CollUtil.isEmpty(directories)) {
            return Result.success();
        }

        List<DirectoryVO> vos = directories.stream().map(DirectoryVO::convertFromEntity).collect(Collectors.toList());
        return Result.success(vos);
    }

    /**
     * 创建文件夹
     *
     * @param parentId      父级目录
     * @param folderName 创建文件夹名称
     * @return cn.xiaoniu.cloud.server.web.response.Result
     * @author 孔明
     * @date 2020-04-27 14:15:58
     */
    @Transactional
    public Result<DirectoryVO> createFolder(Long parentId, String folderName) {
        return Result.success(createDirectory(parentId, folderName, Directory.Type.FOLDER, null));
    }

    /**
     * 创建文件
     *
     * @param parentId 父级目录
     * @param fileName 文件名称
     * @return
     */
    @Transactional
    public Result<DirectoryVO> createFile(Long parentId, String fileName, HttpServletRequest request) {
        Integer fileType = Directory.FileType.getType(request.getContentType());
        return Result.success(createDirectory(parentId, fileName, Directory.Type.FOLDER, fileType));
    }

    /**
     * 创建目录
     *
     * @param parentId      父级目录
     * @param directoryName 目录名称
     * @param directoryType 目录类型
     * @param fileType
     * @return
     */
    protected DirectoryVO createDirectory(Long parentId, String directoryName, Integer directoryType, Integer fileType) {
        if (!Directory.isFile(directoryType) && !Directory.isFolder(directoryType)) {
            throw ThrowsException.requestParamException("目录类型错误");
        }

        // 当前用户信息
        CacheUser cacheUser = AuthorityUtil.getCurrCustomer();

        // 父级目录是否存在
        parentId = Directory.isRoot(parentId) ? cacheUser.getRootDirectoryId() : parentId;
        Directory parentDirectory = findByUserIdAndDirectoryID(parentId, directoryType);
        if (Objects.isNull(parentDirectory)) {
            throw ThrowsException.requestParamException("未获取到当前目录信息");
        }

        if (!parentDirectory.isFolder()) {
            throw ThrowsException.requestParamException("当前目录非文件夹");
        }

        // 文件夹重复检查
        Directory repeatDirectory = findByDirectoryIDAndName(cacheUser.getId(), parentId, directoryName);
        if (Directory.isFolder(directoryType) && Objects.nonNull(repeatDirectory)) {
            throw ThrowsException.requestParamException("目录已存在");
        }
        if (Directory.isFile(directoryType) && Objects.nonNull(repeatDirectory)) {
            throw ThrowsException.requestParamException("文件已存在");
        }

        // 创建文件夹
        Directory directory = EntityUtil.newEntity(Directory.class);
        directory.setUserId(cacheUser.getId());
        directory.setType(directoryType);
        directory.setName(directoryName);
        directory.setParentId(parentDirectory.getId());
        directory.setLevel(parentDirectory.nextLevel());
        directory.setLeftNo(parentDirectory.getRightNo());
        if (directory.isFile()) {
            directory.setFileType(fileType);
            directory.setFileId(IdUtil.getNumberId());
        }
        directory.setRightNo(directory.getLeftNo() + 1);
        directoryAutoDao.saveEntity(directory);

        // 修改其他节点左右值
        parentDirectory.setUpdateTime(DateUtil.getCurrMillis());
        directoryDao.saveEntityUpdateLeftAndRight(parentDirectory);

        return DirectoryVO.convertFromEntity(directory);
    }

    /**
     * 修改文件/文件夹名称
     *
     * @param directoryId   目录ID
     * @param directoryName 目录名称
     * @return
     */
    @Transactional
    public Result<DirectoryVO> updateDirectoryName(Long directoryId, String directoryName) {
        CacheUser cacheUser = AuthorityUtil.getCurrCustomer();
        Directory directory = findByUserIdAndDirectoryID(directoryId);
        if (Objects.isNull(directory)) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "未获取到此目录信息！");
        }

        // 校验名称是否重复
        Directory repeatDirectory = findByDirectoryIDAndName(cacheUser.getId(), directory.getParentId(), directoryName);
        if (Objects.nonNull(repeatDirectory)) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "目录已存在！");
        }

        // 修改目录名称
        Directory directoryUpdate = EntityUtil.update(directory);
        directoryUpdate.setName(directoryName);
        directoryAutoDao.updateEntityById(directoryUpdate);

        directory.setName(directoryName);
        return Result.success(DirectoryVO.convertFromEntity(directory));
    }

    /**
     * 文件夹删除
     *
     * @param directoryId 目录ID
     * @return
     */
    @Transactional
    public Result deleteDirectory(Long directoryId) {
        // 获取点前目录信息
        Directory directory = findByUserIdAndDirectoryID(directoryId);
        if (Objects.isNull(directory)) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "未获取到目录信息！");
        }

        // 删除目录及子目录
        directory.setUpdateTime(DateUtil.getCurrMillis());
        directoryDao.deleteUpdateLeftAndRight(directory);

        return Result.success();
    }

    /**
     * 移动目录
     *
     * @param directoryId 移动目录ID
     * @param newParentId 移动目录新父级目录ID
     * @return
     */
    @Transactional
    public Result moveDirectory(Long directoryId, Long newParentId) {
        // 要移动目录ID
        Directory directory = findByUserIdAndDirectoryID(directoryId);
        if (Objects.isNull(directory)) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "为获取到移动目录信息！");
        }

        // 获取移动目录的所有子目录
        List<Directory> child = directoryDao.findChild(directory);

        // 新父级目录
        Directory newParentDirectory = findByUserIdAndDirectoryID(newParentId);
        if (Objects.isNull(newParentDirectory)) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "未获取目标目录信息！");
        }
        if (newParentDirectory.isFile()) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "当前目录非文件夹！");
        }

        // 开始移动
        directoryDao.moveDirectory(directory, child, newParentDirectory);

        return Result.success();
    }

    /**
     * 通过用户ID和目录ID获取目录信息<br />
     *  加上用户ID参数，避免目录不是此用户下的进行误操作
     * @param directoryId 目录ID
     * @return
     */
    private Directory findByUserIdAndDirectoryID(Long directoryId) {
        return findByUserIdAndDirectoryID(directoryId, null);
    }

    /**
     * 通过用户ID和目录ID获取目录信息<br />
     * 加上用户ID参数，避免目录不是此用户下的进行误操作
     *
     * @param directoryId 目录ID
     * @param type        目录类型
     * @return
     */
    private Directory findByUserIdAndDirectoryID(Long directoryId, Integer type) {
        Directory directorySelect = EntityUtil.select(Directory.class);
        directorySelect.setUserId(AuthorityUtil.getCurrCustomer().getId());
        directorySelect.setId(directoryId);
        if (Objects.nonNull(type)) {
            directorySelect.setType(type);
        }

        return directoryAutoDao.findLastOne(directorySelect);
    }


    private Directory findByDirectoryIDAndName(Long userID, Long parentDirectoryID, String name) {
        return findByDirectoryIDAndName(userID, parentDirectoryID, name);
    }
}