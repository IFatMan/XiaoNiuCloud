package cn.xiaoniu.cloud.server.api.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 孔明
 * @date 2020/4/27 11:23
 * @description cn.xiaoniu.cloud.server.api.model.vo.DirectoryVO
 */
@Data
public class DirectoryVO implements Serializable {

    /**
     * 目录类型1：文件；2：文件夹
     */
    private Integer type;
    /**
     * 文件/文件夹名称
     */
    private String name;
    /**
     * 文件类型（10:图片；20:视频；30:文档；40:音乐；50:种子；60:其他）
     */
    private Long fileType;
    /**
     * 文件ID（当type=1时有值）
     */
    private Long fileId;
    /**
     * 文件名
     */
    private String fileName;

}
