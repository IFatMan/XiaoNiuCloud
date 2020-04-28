package cn.xiaoniu.cloud.server.api.dao.db;

import cn.xiaoniu.cloud.server.api.model.po.Directory;
import org.apache.ibatis.annotations.Param;

/**
 * @author 孔明
 * @date 2020/4/27 16:05
 * @description cn.xiaoniu.cloud.server.api.dao.db.DirectoryDao
 */
public interface DirectoryDao {

    /**
     * 修改其他节点左右值
     *
     * @param parent
     * @return
     */
    int updateLeftAndRight(@Param("parent") Directory parent);

    /**
     * 修改父节点右值
     *
     * @param parent
     * @return
     */
    int updateRight(@Param("parent") Directory parent);

    /**
     * 删除当前节点及子节点
     *
     * @param entity
     * @return
     */
    int delete(@Param("entity") Directory entity);


}
