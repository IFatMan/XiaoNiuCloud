package cn.xiaoniu.cloud.server.web.entity;

import cn.xiaoniu.cloud.server.util.constant.CommonConstant;
import cn.xiaoniu.cloud.server.util.exception.UtilException;
import cn.xiaoniu.cloud.server.util.id.IdUtil;

/**
 * 负责 POJO 类的创建工作
 *
 * @author 孔明
 * @date 2019/11/22 15:35
 * @description cn.xiaoniu.cloud.server.common.web.util.EntityUtil
 */
public class EntityUtil {

    private EntityUtil() {
    }

    /**
     * 初始化一个POJO类
     *
     * @param clazz 类 Class
     * @return POJO
     */
    public static <T extends BaseEntity> T newEntity(Class<T> clazz) {
        return EntityUtil.newEntity(clazz, IdUtil.getNumberId());
    }

    /**
     * 初始化一个POJO类
     *
     * @param clazz 类 Class
     * @param id    主键
     * @return POJO
     */
    public static <T extends BaseEntity> T newEntity(Class<T> clazz, Long id) {
        return EntityUtil.newEntity(clazz, id, System.currentTimeMillis());
    }

    /**
     * 初始化一个POJO类
     *
     * @param clazz      类 Class
     * @param id         主键
     * @param createTime 创建时间
     * @return POJO
     */
    public static <T extends BaseEntity> T newEntity(Class<T> clazz, Long id, Long createTime) {
        T entity = createEntity(clazz);
        entity.setId(id);
        entity.setDel(CommonConstant.NO);
        entity.setCreateTime(createTime);
        return entity;
    }

    /**
     * 更新
     *
     * @param entity 更新 POJO
     * @return POJO
     */
    public static <T extends BaseEntity> T update(T entity) {
        entity.setUpdateTime(System.currentTimeMillis());
        return entity;
    }

    /**
     * 根据ID主键更新
     *
     * @param entity 更新 POJO
     * @return T
     * @author 孔明
     * @date 2019-11-22 16:28:39
     */
    public static <T extends BaseEntity> T updateByPrimary(T entity) {
        Class<T> tClass = (Class<T>) entity.getClass();
        T newEntity = createEntity(tClass);
        newEntity.setId(entity.getId());
        newEntity.setUpdateTime(System.currentTimeMillis());
        return newEntity;
    }

    /**
     * 删除
     *
     * @param entity 删除POJO
     * @return POJO
     */
    public static <T extends BaseEntity> T delete(T entity) {
        entity.setDel(CommonConstant.YES);
        entity.setUpdateTime(System.currentTimeMillis());
        return entity;
    }

    /**
     * 根据主键删除
     *
     * @param entity 删除POJO
     * @return T
     * @author 孔明
     * @date 2019-11-22 16:30:46
     */
    public static <T extends BaseEntity> T deleteByPrimary(T entity) {
        Class<T> tClass = (Class<T>) entity.getClass();
        T newEntity = createEntity(tClass);
        newEntity.setId(entity.getId());
        newEntity.setDel(CommonConstant.YES);
        newEntity.setUpdateTime(System.currentTimeMillis());
        return newEntity;
    }

    /**
     * 主键查找
     *
     * @param id
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> T selectByPrimary(Long id, Class<T> tClass) {
        T t = createEntity(tClass);
        t.setId(id);
        return t;
    }

    /**
     * 普通查询
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> T select(Class<T> tClass) {
        return createEntity(tClass);
    }

    /**
     * 创建一个Entity
     *
     * @param clazz 类 Class
     * @return 新类
     */
    public static <T extends BaseEntity> T createEntity(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new UtilException("创建Entity异常!");
        }
    }

}
