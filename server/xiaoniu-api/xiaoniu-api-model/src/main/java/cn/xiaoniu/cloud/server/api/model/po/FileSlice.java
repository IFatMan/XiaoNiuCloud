package cn.xiaoniu.cloud.server.api.model.po;

import cn.xiaoniu.cloud.server.web.entity.BaseEntity;
import cn.xiaoniu.cloud.server.web.exception.ThrowsException;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>文件分片记录 实体类</p>
 * <p>创建时间:2020-05-06</p>
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
public class FileSlice extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 文件MD5值
     */
    private String md5;
    /**
     * 分片索引
     */
    private Integer index;
    /**
     * 分片大小
     */
    private Long size;
    /**
     * 分片已上传文件大小（单位byte）
     */
    private Long uploadSize;
    /**
     * 存储路径
     */
    private String storagePath;
    /**
     * 分片状态（1：上传中，2：上传完成）
     */
    private Integer status;

    /**
     * 排序规则
     */
    public static final class OrderBy {

        /**
         * 通过index字段倒序排序
         */
        public static final String INDEX_DESC = " index DESC ";

        /**
         * 通过index字段正序排序
         */
        public static final String INDEX_ASC = " index ASC ";

        private OrderBy() {
            throw ThrowsException.privateConstructorException();
        }
    }

    /**
     * 分片上传状态
     */
    public static final class State {

        /**
         * 上传中
         */
        public static final Integer UPLOADING = 1;

        /**
         * 上传完成
         */
        public static final Integer UPLOAD_FINISH = 2;

        private State() {
            throw ThrowsException.privateConstructorException();
        }

    }
}
