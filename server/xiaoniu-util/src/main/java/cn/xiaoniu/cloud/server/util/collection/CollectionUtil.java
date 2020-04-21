package cn.xiaoniu.cloud.server.util.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 孔明
 * @date 2019/11/18 10:53
 * @description cn.xiaoniu.cloud.server.util.collection.CollectionUtil
 */
public class CollectionUtil {

    private CollectionUtil() {

    }

    /**
     * 集合是否为空
     *
     * @param coll 集合
     * @return boolean
     * @author 孔明
     * @date 2019-11-18 10:54:27
     */
    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    /**
     * 创建HashSet
     *
     * @return java.util.Set
     * @author 孔明
     * @date 2019-11-18 16:19:23
     */
    public static Set createHashSet() {
        return new HashSet<>();
    }

}
