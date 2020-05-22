package cn.xiaoniu.cloud.server.api.model.po;

import cn.xiaoniu.cloud.server.web.entity.BaseEntity;
import cn.xiaoniu.cloud.server.web.exception.ThrowsException;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

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
    private Integer fileType;
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
            throw ThrowsException.privateConstructorException();
        }
    }

    /**
     * 目录类型
     */
    public static class Type {
        private Type() {
            throw ThrowsException.privateConstructorException();
        }

        /**
         * 文件
         */
        public static final Integer FILE = 1;

        /**
         * 目录
         */
        public static final Integer FOLDER = 2;

    }

    /**
     * 文件类型
     */
    public static final class FileType {

        /**
         * 文档Mime
         */
        private static final String[] documentMimes = {
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.template",
                "application/vnd.ms-word.document.macroEnabled.12",
                "application/vnd.ms-word.template.macroEnabled.12",
                "application/vnd.ms-excel",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.template",
                "application/vnd.ms-excel.sheet.macroEnabled.12",
                "application/vnd.ms-excel.template.macroEnabled.12",
                "application/vnd.ms-excel.addin.macroEnabled.12",
                "application/vnd.ms-excel.sheet.binary.macroEnabled.12",
                "application/vnd.ms-powerpoint",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                "application/vnd.openxmlformats-officedocument.presentationml.template",
                "application/vnd.openxmlformats-officedocument.presentationml.slideshow",
                "application/vnd.ms-powerpoint.addin.macroEnabled.12",
                "application/vnd.ms-powerpoint.presentation.macroEnabled.12",
                "application/vnd.ms-powerpoint.slideshow.macroEnabled.12",
        };

        /**
         * 图片
         */
        public static final Integer PICTURE = 10;

        /**
         * 视频
         */
        public static final Integer VIDEO = 20;

        /**
         * 文档
         */
        public static final Integer DOCUMENT = 30;

        /**
         * 音乐
         */
        public static final Integer MUSIC = 40;

        /**
         * 种子
         */
        public static final Integer SEED = 50;

        /**
         * 其他
         */
        public static final Integer OTHER = 60;

        private FileType() {
            throw ThrowsException.privateConstructorException();
        }

        public static Integer getType(final String contentType) {
            String mime = contentType.toLowerCase();
            if (StringUtils.isBlank(mime)) {
                return OTHER;
            }
            if (mime.contains("image")) {
                return PICTURE;
            } else if (mime.contains("audio/mp3")) {
                return MUSIC;
            } else if (mime.contains("audio") || mime.contains("video")) {
                return VIDEO;
            } else if (isDocument(contentType)) {
                return DOCUMENT;
            } else if (mime.contains("application/x-bittorrent")) {
                return SEED;
            } else {
                return OTHER;
            }
        }

        private static boolean isDocument(String contentType) {
            for (String documentMime : documentMimes) {
                if (contentType.contains(documentMime)) {
                    return true;
                }
            }
            return false;
        }
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
     * 判断目录parent是否是此节点的父系节点
     *
     * @param parent 父节点
     * @return true: 父系节点中的一个；false：不是父系节点
     */
    public boolean isParent(Directory parent) {
        return this.leftNo > parent.getLeftNo() && this.rightNo < parent.getRightNo();
    }

    /**
     * 是否包含子目录
     *
     * @return true:有子目录；false:没有子目录
     */
    public boolean hasChild() {
        return this.rightNo - this.level > 1;
    }

    /**
     * 获取子节点数量
     *
     * @return
     */
    public int getChildSize() {
        if (hasChild()) {
            return (this.rightNo - this.leftNo - 1) / 2;
        }
        return 0;
    }

    /**
     * 判断当前目录是否是文件夹
     *
     * @return
     */
    public boolean isFolder() {
        return Objects.equals(this.type, Type.FOLDER);
    }

    /**
     * 判断是否是文件夹类型
     *
     * @param type 目录类型
     * @return
     */
    public static boolean isFolder(Integer type) {
        return Objects.equals(type, Type.FOLDER);
    }

    /**
     * 判断当前目录是否是文件
     *
     * @return
     */
    public boolean isFile() {
        return Objects.equals(this.type, Type.FILE);
    }

    /**
     * 判断是否是文件类型
     *
     * @param type 目录类型
     * @return
     */
    public static boolean isFile(Integer type) {
        return Objects.equals(type, Type.FILE);
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
