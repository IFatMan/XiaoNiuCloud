package cn.xiaoniu.cloud.server.api.model.po;

import cn.xiaoniu.cloud.server.web.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

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
    /**
     * 父级
     */
    private Long parentId;
    /**
     * 目录级别、深度
     */
    private Integer level;
    /**
     * 左值
     */
    private Integer leftNo;
    /**
     * 右值
     */
    private Integer rightNo;


    /**
     * 常量
     */
    public static class Constant {

        /**
         * 根目录左值
         */
        public static final Integer LEFT = 1;

        /**
         * 根目录右值
         */
        public static final Integer RIGHT = 2;

        /**
         * 根目录父级ID
         */
        public static final Long ROOT_PARENT_ID = 0L;

        /**
         * 根目录名称
         */
        public static final String ROOT_NAME = "Root";

        /**
         * 根目录级别
         */
        public static final Integer ROOT_LEVEL = 1;

        /**
         * 最大文件夹长度
         */
        public static final Integer MAX_DIRECTORY_NAME_LENGTH = 20;

        private Constant() {
        }
    }

    /**
     * 目录类型
     */
    public static class Type {
        private Type() {
        }

        /**
         * 文件
         */
        public static final Integer FILE = 1;

        /**
         * 目录
         */
        public static final Integer DIRECTORY = 2;

    }

    /**
     * 下一级级别
     *
     * @return
     */
    public Integer nextLevel() {
        return this.level + 1;
    }

    /**
     * 父级目录是否是根目录
     *
     * @param parentId 父级目录ID
     * @return
     */
    public static boolean isRoot(Long parentId) {
        return Constant.ROOT_PARENT_ID.equals(parentId);
    }
}
