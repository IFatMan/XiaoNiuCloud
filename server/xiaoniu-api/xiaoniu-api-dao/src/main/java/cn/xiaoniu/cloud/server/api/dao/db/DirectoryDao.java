package cn.xiaoniu.cloud.server.api.dao.db;

import cn.xiaoniu.cloud.server.api.model.po.Directory;
import org.apache.ibatis.annotations.Param;

/**
 * @author 孔明
 * @date 2020/4/27 16:05
 * @description cn.xiaoniu.cloud.server.api.dao.db.DirectoryDao
 */
public interface DirectoryDao {

    int updateLeftAndRight(@Param("parent") Directory parent);

    int updateRight(@Param("parent") Directory parent);


}
