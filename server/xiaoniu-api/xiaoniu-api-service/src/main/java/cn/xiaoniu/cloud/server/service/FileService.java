package cn.xiaoniu.cloud.server.service;

import cn.xiaoniu.cloud.server.api.dao.cache.SystemConfigDao;
import cn.xiaoniu.cloud.server.api.dao.db.FileAutoDao;
import cn.xiaoniu.cloud.server.api.model.po.File;
import cn.xiaoniu.cloud.server.api.model.vo.FileUploadVO;
import cn.xiaoniu.cloud.server.util.FileUtil;
import cn.xiaoniu.cloud.server.web.entity.EntityUtil;
import cn.xiaoniu.cloud.server.web.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 孔明
 * @date 2020/5/6 15:23
 * @description cn.xiaoniu.cloud.server.service.FileService
 */
@Service
public class FileService {

    @Autowired
    private SystemConfigDao systemConfigDao;

    @Autowired
    private FileAutoDao fileAutoDao;

    /**
     * 判断文件是否上传
     *
     * @param md5
     * @param size
     * @return
     */
    public Result<FileUploadVO> isUpload(String md5, Long size) {
        long md5Code = FileUtil.md5Code(md5);
        File select = File.builder().md5Code(md5Code).md5(md5).build();
        File file = fileAutoDao.findOne(select);
        if (Objects.nonNull(file)) {
            return Result.success(FileUploadVO.hasUpload(file.getState()));
        }

        Integer fileSliceSize = systemConfigDao.getFileSliceSize();
        long slice = FileUtil.slice(size, fileSliceSize);

        file = EntityUtil.newEntity(File.class);
        file.setMd5(md5);
        file.setMd5Code(md5Code);
        file.setSize(size);
        file.setSlice(slice);
        file.setUploadSize(0L);
        file.setState(File.State.READY_UPLOAD);
        fileAutoDao.saveEntity(file);

        return Result.success(FileUploadVO.notUpload());
    }
}
