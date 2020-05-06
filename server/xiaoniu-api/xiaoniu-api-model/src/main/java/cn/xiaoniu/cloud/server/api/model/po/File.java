package cn.xiaoniu.cloud.server.api.model.po;

import cn.xiaoniu.cloud.server.web.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>文件上传记录 实体类</p>
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
     * 磁盘存储地址
     */
    private String storagePath;
    /**
     * 文件大小（单位byte）
     */
    private Long size;
    /**
     * 是否分片（1：未分片；2：不分片）
     */
    private Long slice;
    /**
     * 已上传文件大小（单位byte）
     */
    private Long uploadSize;
    /**
     * 状态（1：准备上传；2：上传中；3：合并中；4：上传完成）
     */
    private Integer state;

    /**
     * 是否分片
     */
    public static final class Slice {

        /**
         * 分片
         */
        public static final Integer YES = 1;

        /**
         * 不分片
         */
        public static final Integer NO = 2;

        private Slice() {
        }
    }

    /**
     * 上传状态
     */
    public static final class State {

        /**
         * 准备上传
         */
        public static final Integer READY_UPLOAD = 1;

        /**
         * 上传中
         */
        public static final Integer UPLOADING = 2;

        /**
         * 上传完成，等待合并
         */
        public static final Integer WAITING_MERGING = 3;

        /**
         * 合并中
         */
        public static final Integer MERGING = 4;

        /**
         * 上传完成
         */
        public static final Integer FINISH = 5;


        private State() {
        }

    }
}
