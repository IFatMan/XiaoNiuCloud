package cn.xiaoniu.cloud.server.api;

import cn.xiaoniu.cloud.server.api.config.SystemConfig;
import cn.xiaoniu.cloud.server.api.dao.cache.SystemConfigDao;
import cn.xiaoniu.cloud.server.web.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 孔明
 * @date 2020/5/6 16:43
 * @description cn.xiaoniu.cloud.server.api.SystemInitialization
 */
@Component
@EnableConfigurationProperties(SystemConfig.class)
public class SystemInitialization implements ApplicationRunner {

    @Autowired
    private SystemConfigDao systemConfigDao;

    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Log.info("项目启动成功，现在进行系统初始化。。。。。。。。。。");
        systemConfigDao.setFileStoragePath(systemConfig.getFileStoragePath());
        systemConfigDao.setFileSliceSize(systemConfig.getFileSliceSize());
        Log.info("项目启动成功，现在进行系统初始化完成");
    }
}
