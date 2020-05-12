package cn.xiaoniu.cloud.server.service;

import cn.hutool.core.collection.CollUtil;
import cn.xiaoniu.cloud.server.api.dao.cache.SystemConfigDao;
import cn.xiaoniu.cloud.server.api.dao.db.FileAutoDao;
import cn.xiaoniu.cloud.server.api.dao.db.FileSliceAutoDao;
import cn.xiaoniu.cloud.server.api.model.po.File;
import cn.xiaoniu.cloud.server.api.model.po.FileSlice;
import cn.xiaoniu.cloud.server.api.model.vo.FileSliceUploadVO;
import cn.xiaoniu.cloud.server.api.model.vo.FileUploadVO;
import cn.xiaoniu.cloud.server.util.FileUtil;
import cn.xiaoniu.cloud.server.web.entity.EntityUtil;
import cn.xiaoniu.cloud.server.web.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    private FileSliceAutoDao fileSliceAutoDao;

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
}
