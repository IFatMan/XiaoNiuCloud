package cn.xiaoniu.cloud.server.api.model.vo;

import cn.xiaoniu.cloud.server.api.model.po.File;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author 孔明
 * @date 2020/5/6 15:27
 * @description cn.xiaoniu.cloud.server.api.model.vo.FileUploadVO
 */
@Getter
@Setter
@ApiModel("是否已经上传文件")
public class FileUploadVO implements Serializable {

    @ApiModelProperty(name = "是否上传,true:已经上传过；false：未上传过")
    private Boolean isUpload;

    @ApiModelProperty(name = "分片数量")
    private Integer sliceSize;

    @ApiModelProperty(name = "是否已经上传完成,1：准备上传；2：上传中；3：上传完成待合并；4：合并中；5：上传完成")
    private Integer state;

    @ApiModelProperty(name = "分片上传进度")
    private List<FileSliceUploadVO> sliceUploadVOS;

    /**
     * 未上传返回
     *
     * @return
     */
    public static FileUploadVO notUpload() {
        FileUploadVO vo = new FileUploadVO();
        vo.setIsUpload(Boolean.FALSE);
        return vo;
    }

    /**
     * 已经上传返回
     *
     * @param file
     * @return
     */
    public static FileUploadVO hasUpload(File file, List<FileSliceUploadVO> vos) {
        FileUploadVO vo = new FileUploadVO();
        vo.setIsUpload(Boolean.TRUE);
        vo.setSliceSize(file.getSliceSize());
        vo.setState(file.getState());
        vo.setSliceUploadVOS(vos);
        return vo;
    }

}
