package cn.xiaoniu.cloud.server.api.dao.db;

import cn.xiaoniu.cloud.server.api.model.po.FileSlice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>文件分片记录 DAO 接口</p>
 * <p>创建时间:2020-05-06</p>
 * <p>公司信息:Developed By KongMing</p>
 *
 * @author auto generator
 */
@Mapper
public interface FileSliceAutoDao {

    /**
     * 保存数据
     *
     * @param entity
     * @return
     */
    int saveEntity(@Param("entity") FileSlice entity);

    /**
     * 批量保存数据
     *
     * @param entities
     * @return
     */
    int saveEntities(@Param("entities") List<FileSlice> entities);

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
    int deleteByEntity(@Param("entity") FileSlice entity);

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
    int removeByEntity(@Param("entity") FileSlice entity);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    int updateEntityById(@Param("entity") FileSlice entity);

    /**
     * 更新数据
     *
     * @param entity
     * @param record
     * @return
     */
    int updateEntityByPO(@Param("entity") FileSlice entity, @Param("record") FileSlice record);

    /**
     * 获取数据
     *
     * @param id
     * @return
     */
    FileSlice findById(@Param("id") Long id);

    /**
     * 获取一条数据
     *
     * @param entity
     * @return
     */
    FileSlice findOne(@Param("entity") FileSlice entity);

    /**
     * 获取最新的一条数据 , 通过ID倒叙实现
     *
     * @param entity
     * @return
     */
    FileSlice findLastOne(@Param("entity") FileSlice entity);

    /**
     * 根据指定字段名和值获取数据
     *
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @return
     */
    <T> List<FileSlice> findByFieldName(@Param("fieldName") String fieldName, @Param("fieldValue") T fieldValue);

    /**
     * 根据指定字段名和值获取最后一条数据
     *
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @return
     */
    <T> FileSlice findLastOneByFieldName(@Param("fieldName") String fieldName, @Param("fieldValue") T fieldValue);

    /**
     * 获取数量
     *
     * @param entity
     * @return
     */
    long countByEntity(@Param("entity") FileSlice entity);

    /**
     * 批量获取通过ID
     *
     * @param ids
     * @return
     */
    List<FileSlice> listByIdIn(@Param("ids") List<Long> ids);

    /**
     * 列表获取数据
     *
     * @param entity
     * @return
     */
    List<FileSlice> listByEntity(@Param("entity") FileSlice entity);

    /**
     * 分页获取数据
     *
     * @param entity
     * @return
     */
    List<FileSlice> pageByEntity(@Param("entity") FileSlice entity);
}
