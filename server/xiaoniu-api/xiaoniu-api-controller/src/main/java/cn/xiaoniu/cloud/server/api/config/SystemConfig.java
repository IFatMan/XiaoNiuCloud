package cn.xiaoniu.cloud.server.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 孔明
 * @date 2020/5/6 16:44
 * @description cn.xiaoniu.cloud.server.api.config.SystemConfig
 */
@ConfigurationProperties(prefix = "system.config")
public class SystemConfig {

    /**
     * 文件存储路径
     */
    private String fileStoragePath;

    /**
     * 文件分片大小(单位字节)
     */
    private Long fileSliceSize;

    public String getFileStoragePath() {
        return fileStoragePath;
    }

    public void setFileStoragePath(String fileStoragePath) {
        this.fileStoragePath = fileStoragePath;
    }

    public Long getFileSliceSize() {
        return fileSliceSize;
    }

    public void setFileSliceSize(Long fileSliceSize) {
        this.fileSliceSize = fileSliceSize;
    }
}
