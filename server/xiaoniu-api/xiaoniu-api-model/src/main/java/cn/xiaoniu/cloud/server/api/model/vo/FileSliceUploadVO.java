package cn.xiaoniu.cloud.server.api.model.vo;

import cn.xiaoniu.cloud.server.api.model.po.FileSlice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author 孔明
 * @date 2020/5/12 15:35
 * @description cn.xiaoniu.cloud.server.api.model.vo.FileSliceUploadVO
 */
@Getter
@Setter
@ApiModel("文件分片上传进度")
public class FileSliceUploadVO implements Serializable {

    @ApiModelProperty(name = "分片ID")
    private Long id;

    @ApiModelProperty(name = "分片索引")
    private Integer index;

    @ApiModelProperty(name = "分片大小")
    private Long size;

    @ApiModelProperty(name = "已上传大小")
    private Long uploadSize;

    @ApiModelProperty(name = "分片状态（1：上传中，2：上传完成）")
    private Integer state;

    /**
     * 从POJO转换
     *
     * @param slice 分片信息
     * @return
     */
    public static FileSliceUploadVO convertFromPOJO(FileSlice slice) {
        FileSliceUploadVO vo = new FileSliceUploadVO();
        vo.setId(slice.getId());
        vo.setIndex(slice.getIndex());
        vo.setSize(slice.getSize());
        vo.setUploadSize(slice.getUploadSize());
        vo.setState(slice.getStatus());
        return vo;
    }

}
