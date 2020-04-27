package cn.xiaoniu.cloud.server.api.model.po;

import cn.xiaoniu.cloud.server.api.model.vo.DirectoryVO;
import cn.xiaoniu.cloud.server.web.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * <p>用户目录 实体类</p>
 * <p>创建时间:2020-04-27</p>
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
public class Directory extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 目录级别、深度
     */
    private Integer level;
    /**
     * 目录类型1：文件；2：文件夹
     */
    private Integer type;
    /**
     * 文件/文件夹名称
     */
    private String name;
    /**
     * 父级
     */
    private Long parentId;
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

    /**
     * 转换
     *
     * @param directory
     * @return
     */
    public static DirectoryVO convertFromEntity(Directory directory) {
        DirectoryVO vo = new DirectoryVO();
        BeanUtils.copyProperties(directory, vo);
        return vo;
    }
}
