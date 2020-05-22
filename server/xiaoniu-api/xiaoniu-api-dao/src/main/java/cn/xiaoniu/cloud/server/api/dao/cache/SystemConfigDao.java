package cn.xiaoniu.cloud.server.api.dao.cache;

import cn.xiaoniu.cloud.server.api.common.RedisSourceName;
import cn.xiaoniu.cloud.server.web.redis.RedisDataSourceHolder;
import cn.xiaoniu.cloud.server.web.redis.RedisSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author 孔明
 * @date 2020/5/6 15:47
 * @description cn.xiaoniu.cloud.server.api.dao.cache.SystemConfigDao
 */
@Component
public class SystemConfigDao {

    /**
     * 获取文件存储路径
     *
     * @return
     */
    @RedisSource(RedisSourceName.API)
    public String getFileStoragePath() {
        String key = StringUtils.join(Constant.CONFIG_PREFIX, Constant.FILE_STORAGE_PATH);
        return RedisDataSourceHolder.execute(redisUtil -> redisUtil.get(key));
    }

    /**
     * 获取文件分片大小
     *
     * @return
     */
    @RedisSource(RedisSourceName.API)
    public Integer getFileSliceSize() {
        String key = StringUtils.join(Constant.CONFIG_PREFIX, Constant.FILE_SLICE_SIZE);
        return RedisDataSourceHolder.execute(redisUtil -> redisUtil.get(key));
    }

    /**
     * 设置文件存储路径
     *
     * @param fileStoragePath
     */
    @RedisSource(RedisSourceName.API)
    public void setFileStoragePath(String fileStoragePath) {
        String key = StringUtils.join(Constant.CONFIG_PREFIX, Constant.FILE_STORAGE_PATH);
        RedisDataSourceHolder.execute(redis -> redis.set(key, fileStoragePath));
    }

    /**
     * 设置文件分片大小
     *
     * @param fileSliceSize
     */
    @RedisSource(RedisSourceName.API)
    public void setFileSliceSize(Long fileSliceSize) {
        String key = StringUtils.join(Constant.CONFIG_PREFIX, Constant.FILE_SLICE_SIZE);
        RedisDataSourceHolder.execute(redis -> redis.set(key, fileSliceSize));
    }

    private static final class Constant {

        /**
         * 系统配置前缀
         */
        private static final String CONFIG_PREFIX = "SYSTEM:CONFIG:";

        /**
         * 文件存储路径
         */
        private static final String FILE_STORAGE_PATH = "FILE_STORAGE_PATH";

        /**
         * 文件分片大小
         */
        private static final String FILE_SLICE_SIZE = "FILE_SLICE_SIZE";

        private Constant() {

        }

    }

}
