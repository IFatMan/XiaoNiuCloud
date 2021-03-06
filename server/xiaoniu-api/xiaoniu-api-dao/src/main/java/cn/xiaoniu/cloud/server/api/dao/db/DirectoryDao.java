package cn.xiaoniu.cloud.server.api.dao.db;

import cn.xiaoniu.cloud.server.api.model.po.Directory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 孔明
 * @date 2020/4/27 16:05
 * @description cn.xiaoniu.cloud.server.api.dao.db.DirectoryDao
 */
public interface DirectoryDao {

    /**
     * 获取所有的子目录，包含参数目录和已删除目录
     *
     * @param directory 参数目录
     * @return
     */
    List<Directory> findChild(@Param("entity") Directory directory);

    /**
     * 新增目录修改修改目录左右值
     *
     * @param parent
     * @return
     */
    int saveEntityUpdateLeftAndRight(@Param("parent") Directory parent);

    /**
     * 删除当前节点及子节点
     *
     * @param entity
     * @return
     */
    int deleteUpdateLeftAndRight(@Param("entity") Directory entity);

    /**
     * 移动目录
     *
     * @param directory          要移动的目录
     * @param child  子目录
     * @param newParentDirectory 新父级目录
     */
    int moveDirectory(@Param("entity") Directory directory,
                      @Param("child") List<Directory> child,
                      @Param("newParent") Directory newParentDirectory);
}
