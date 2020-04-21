package cn.xiaoniu.cloud.server.util.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map 集合工具类
 *
 * @author 孔明
 * @date 2019/11/18 10:57
 * @description cn.xiaoniu.cloud.server.util.collection.MapUtil
 */
public class MapUtil {

    private MapUtil() {
    }

    /**
     * Map集合是否为空
     *
     * @param map Map集合
     * @return boolean
     * @author 孔明
     * @date 2019-11-18 10:55:36
     */
    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 将单一键值对转换为Map
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @param key 键
     * @param value 值
     * @return {@link HashMap}
     */
    public static <K, V> HashMap<K, V> of(K key, V value) {
        HashMap<K, V> map = createEmptyHashMap();
        map.put(key, value);
        return map;
    }

    /**
     * 创建空HashMap集合
     *
     * @return java.util.HashMap
     * @author 孔明
     * @date 2019-11-18 13:45:02
     */
    public static HashMap createEmptyHashMap() {
        return new HashMap();
    }

    /**
     * 获取所有Value
     *
     * @param map Map 集合
     * @return java.util.Set<V>
     * @author 孔明
     * @date 2019-11-18 16:22:58
     */
    public static <K, V> Set<V> getValues(Map<K, V> map) {
        Set<V> result = CollectionUtil.createHashSet();
        if (isEmpty(map)) {
            return result;
        }
        for (K k : map.keySet()) {
            result.add(map.get(k));
        }
        return result;
    }

}

