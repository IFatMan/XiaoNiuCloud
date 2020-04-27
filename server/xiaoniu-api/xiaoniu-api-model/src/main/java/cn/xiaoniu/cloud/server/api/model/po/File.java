package cn.xiaoniu.cloud.server.api.model.po;

import cn.xiaoniu.cloud.server.web.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>文件上传记录 实体类</p>
 * <p>创建时间:2020-04-26</p>
 * <p>公司信息:Developed By KongMing </p>
 *
 * @author auto generator
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class File extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 32位MD5值
     */
    private String md5;
    /**
     * 32位MD5计算的值
     */
    private Long md5Code;
    /**
     * MIME
     */
    private String mime;
    /**
     * 磁盘存储地址
     */
    private String storagePath;
    /**
     * 文件大小（单位byte）
     */
    private Long size;
    /**
     * 状态（1：准备上传；2：上传中；3：合并中；4：上传完成）
     */
    private Integer state;
}
