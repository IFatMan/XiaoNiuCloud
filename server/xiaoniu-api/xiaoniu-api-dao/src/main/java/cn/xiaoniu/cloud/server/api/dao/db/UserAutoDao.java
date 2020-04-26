package cn.xiaoniu.cloud.server.api.dao.db;

import cn.xiaoniu.cloud.server.api.model.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> DAO 接口</p>
 * <p>创建时间:2020-04-26</p>
 * <p>公司信息:Developed By KongMing</p>
 *
 * @author auto generator
 */
@Mapper
public interface UserAutoDao {

    /**
     * 保存数据
     *
     * @param entity
     * @return
     */
    int saveEntity(@Param("entity") User entity);

    /**
     * 批量保存数据
     *
     * @param entities
     * @return
     */
    int saveEntities(@Param("entities") List<User> entities);

    /**
     * 逻辑删除数据
     *
     * @param id
     * @return
     */
    int deleteById(@Param("id") Long id);

    /**
     * 逻辑删除数据
     *
     * @param entity
     * @return
     */
    int deleteByEntity(@Param("entity") User entity);

    /**
     * 物理删除数据
     *
     * @param id
     * @return
     */
    int removeById(@Param("id") Long id);

    /**
     * 物理删除数据
     *
     * @param entity
     * @return
     */
    int removeByEntity(@Param("entity") User entity);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    int updateEntityById(@Param("entity") User entity);

    /**
     * 更新数据
     *
     * @param entity
     * @param record
     * @return
     */
    int updateEntityByPO(@Param("entity") User entity, @Param("record") User record);

    /**
     * 获取数据
     *
     * @param id
     * @return
     */
    User findById(@Param("id") Long id);

    /**
     * 获取一条数据
     *
     * @param entity
     * @return
     */
    User findOne(@Param("entity") User entity);

    /**
     * 获取最新的一条数据 , 通过ID倒叙实现
     *
     * @param entity
     * @return
     */
    User findLastOne(@Param("entity") User entity);

    /**
     * 根据指定字段名和值获取数据
     *
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @return
     */
    <T> List<User> findByFieldName(@Param("fieldName") String fieldName, @Param("fieldValue") T fieldValue);

    /**
     * 根据指定字段名和值获取最后一条数据
     *
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @return
     */
    <T> User findLastOneByFieldName(@Param("fieldName") String fieldName, @Param("fieldValue") T fieldValue);

    /**
     * 获取数量
     *
     * @param entity
     * @return
     */
    long countByEntity(@Param("entity") User entity);

    /**
     * 批量获取通过ID
     *
     * @param ids
     * @return
     */
    List<User> listByIdIn(@Param("ids") List<Long> ids);

    /**
     * 列表获取数据
     *
     * @param entity
     * @return
     */
    List<User> listByEntity(@Param("entity") User entity);

    /**
     * 分页获取数据
     *
     * @param entity
     * @return
     */
    List<User> pageByEntity(@Param("entity") User entity);
}
