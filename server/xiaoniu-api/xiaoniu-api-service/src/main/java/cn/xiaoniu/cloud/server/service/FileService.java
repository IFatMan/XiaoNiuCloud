package cn.xiaoniu.cloud.server.service;

import cn.hutool.core.collection.CollUtil;
import cn.xiaoniu.cloud.server.api.common.CacheUser;
import cn.xiaoniu.cloud.server.api.dao.cache.SystemConfigDao;
import cn.xiaoniu.cloud.server.api.dao.db.DirectoryAutoDao;
import cn.xiaoniu.cloud.server.api.dao.db.FileAutoDao;
import cn.xiaoniu.cloud.server.api.dao.db.FileSliceAutoDao;
import cn.xiaoniu.cloud.server.api.model.po.Directory;
import cn.xiaoniu.cloud.server.api.model.po.File;
import cn.xiaoniu.cloud.server.api.model.po.FileSlice;
import cn.xiaoniu.cloud.server.api.model.vo.FileSliceUploadVO;
import cn.xiaoniu.cloud.server.api.model.vo.FileUploadVO;
import cn.xiaoniu.cloud.server.util.FileUtil;
import cn.xiaoniu.cloud.server.web.authority.AuthorityUtil;
import cn.xiaoniu.cloud.server.web.entity.EntityUtil;
import cn.xiaoniu.cloud.server.web.response.Result;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 孔明
 * @date 2020/5/6 15:23
 * @description cn.xiaoniu.cloud.server.service.FileService
 */
@Service
public class FileService extends DirectoryService {

    @Autowired
    private SystemConfigDao systemConfigDao;

    @Autowired
    private FileAutoDao fileAutoDao;

    @Autowired
    private FileSliceAutoDao fileSliceAutoDao;

    @Autowired
    private DirectoryAutoDao directoryAutoDao;

    /**
     * 判断文件是否上传
     *
     * @param md5
     * @param size
     * @return
     */
    public Result<FileUploadVO> isUpload(String md5, Long size) {

        // 查看文件是否上传过
        long md5Code = FileUtil.md5Code(md5);
        File select = File.builder().md5Code(md5Code).md5(md5).size(size).build();
        File file = fileAutoDao.findOne(select);

        // 没有上传过
        if (Objects.isNull(file)) {
            return Result.success(FileUploadVO.notUpload());
        }

        // 上传过获取分片上传进度
        FileSlice selectSlice = EntityUtil.select(FileSlice.class);
        selectSlice.setOrderBy(FileSlice.OrderBy.INDEX_ASC); // 通过`index`字段正序排序
        selectSlice.setMd5(file.getMd5());
        List<FileSlice> slices = fileSliceAutoDao.listByEntity(selectSlice);
        if (CollUtil.isEmpty(slices)) {
            // TODO 删除上传记录
        }

        List<FileSliceUploadVO> vos = slices.stream()
                .map(FileSliceUploadVO::convertFromPOJO).collect(Collectors.toList());

        return Result.success(FileUploadVO.hasUpload(file, vos));
    }

    @Transactional
    public Result<FileSliceUploadVO> readyUpload(Long directoryId, String md5, Long size, String name, Integer fileType) {
        CacheUser cacheUser = AuthorityUtil.getCurrCustomer();
        Directory selectDirectory = EntityUtil.select(Directory.class);
        selectDirectory.setId(directoryId);
        selectDirectory.setUserId(cacheUser.getId());
        Directory directory = directoryAutoDao.findLastOne(selectDirectory);
        if (Objects.isNull(directory)) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "未获取到目录信息！");
        }

        if (!directory.isFile()) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "此目录非文件目录!");
        }

        super.createDirectory(directoryId, name, Directory.Type.FOLDER, fileType);

        long md5Code = FileUtil.md5Code(md5);
        File select = File.builder().md5Code(md5Code).md5(md5).size(size).build();
        File file = fileAutoDao.findOne(select);
        if (Objects.nonNull(file)) {
            return Result.success();
        }

        // 保存文件信息
        int sliceSize = systemConfigDao.getFileSliceSize();
        file = EntityUtil.newEntity(File.class);
        file.setMd5(md5);
        file.setMd5Code(md5Code);
        file.setSize(size);
        file.setSliceSize(sliceSize);
        file.setUploadSize(0L);
        file.setState(File.State.READY);
        fileAutoDao.saveEntity(file);

        // 分片信息
        List<FileSlice> slices = new ArrayList<>(sliceSize);
        FileSlice slice;
        for (int i = 1; i <= sliceSize; i++) {
            slice = EntityUtil.newEntity(FileSlice.class);
            slice.setIndex(i);
            slice.setMd5(md5);
            slice.setSize(FileUtil.getSliceSize(i, Long.valueOf(sliceSize), size));
            slice.setUploadSize(0L);
            slice.setStoragePath(md5 + "_" + i);
            slice.setStatus(FileSlice.State.UPLOADING);
            slices.add(slice);
        }
        fileSliceAutoDao.saveEntities(slices);

        List<FileSliceUploadVO> vos = slices.stream()
                .map(FileSliceUploadVO::convertFromPOJO).collect(Collectors.toList());

        return Result.success(vos);
    }
}
