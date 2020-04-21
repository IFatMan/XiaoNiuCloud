package cn.xiaoniu.cloud.server.web.redis;

import cn.xiaoniu.cloud.server.util.AssertUtil;
import cn.xiaoniu.cloud.server.util.exception.UtilException;
import cn.xiaoniu.cloud.server.web.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Reids 操作工具类
 *
 * @author 孔明
 * @date 2019/12/10 14:10
 * @description cn.xiaoniu.cloud.server.web.redis.RedisUtil
 */
public class RedisUtil {

    private RedisTemplate<String, Object> redis;

    @Autowired
    public void setRedis(RedisTemplate<String, Object> redisTemplate) {
        this.redis = redisTemplate;
    }

    // ==============================================  通用命令 ==============================================

    /**
     * 该命令用于在 key 存在是删除 key。
     *
     * @param key key
     * @return boolean
     * @author 孔明
     * @date 2019-12-10 14:16:39
     */
    public Boolean del(String key) {
        Log.debug("RedisUtil: Delete key[{}] from redis !", key);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            return redis.delete(key);
        } catch (Exception ex) {
            Log.error("RedisUtil: Delete key exception from redis ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 序列化给定key的值,并返回被序列化的值。
     *
     * @param key KEY
     * @return byte[] key对应value序列化后的值
     * @author 孔明
     * @date 2019-12-10 14:41:43
     */
    public byte[] dump(String key) {
        Log.debug("RedisUtil: Serialize the value for the given key[{}] from redis !", key);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            return redis.dump(key);
        } catch (Exception ex) {
            Log.error("RedisUtil: Serialize the value for the given key exception from redis ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 检测Redis中是否存在Key
     *
     * @param key KEY
     * @return java.lang.Boolean true:存在
     * @author 孔明
     * @date 2019-12-10 15:17:50
     */
    public Boolean exists(String key) {
        Log.debug("RedisUtil: Check the key[{}] exists in redis ! ", key);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            return redis.hasKey(key);
        } catch (Exception ex) {
            Log.error("RedisUtil: Check the key exists in redis ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 设置Key的过期时间
     *
     * @param key     Key
     * @param seconds 时间,单位秒
     * @return java.lang.Boolean
     * @author 孔明
     * @date 2019-12-10 15:33:13
     */
    public Boolean expire(String key, long seconds) {
        Log.debug("RedisUtil: Set key[{}] to expire after [{}] seconds !", key, seconds);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            return redis.expire(key, seconds, TimeUnit.SECONDS);
        } catch (Exception ex) {
            Log.error("RedisUtil: Set the expiration time exception for the key ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 设置Key的过期时间
     *
     * @param key          Key
     * @param milliSeconds 时间,单位毫秒
     * @return java.lang.Boolean
     * @author 孔明
     * @date 2019-12-10 15:33:13
     */
    public Boolean pExpire(String key, long milliSeconds) {
        Log.debug("RedisUtil: Set key[{}] to expire after [{}] milliSeconds !", key, milliSeconds);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            return redis.expire(key, milliSeconds, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            Log.error("RedisUtil: Set the expiration time exception for the key ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 设置给定key的过期时间
     *
     * @param key  Key
     * @param date 过期时间
     * @return java.lang.Boolean
     * @author 孔明
     * @date 2019-12-10 15:41:48
     */
    public Boolean expireAt(String key, Date date) {
        Log.debug("RedisUtil: Set key[{}] to expire at [{}] !", key, date);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            AssertUtil.isNotNull(date, "RedisUtil: The date is null !");
            return redis.expireAt(key, date);
        } catch (Exception ex) {
            Log.error("RedisUtil: Set the expiration time exception for the key ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 设置给定key的过期时间
     *
     * @param key          Key
     * @param milliSeconds 过期时间,单位毫秒
     * @return java.lang.Boolean
     * @author 孔明
     * @date 2019-12-10 15:41:48
     */
    public Boolean pExpireAt(String key, long milliSeconds) {
        Log.debug("RedisUtil: Set key[{}] to expire at [{}] !", key, milliSeconds);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            return redis.expireAt(key, new Date(milliSeconds));
        } catch (Exception ex) {
            Log.error("RedisUtil: Set the expiration time exception for the key ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 模糊匹配给定模式的key
     *
     * @param pattern 匹配模式
     * @return java.util.Set<java.lang.String>
     * @author 孔明
     * @date 2019-12-10 15:50:17
     */
    public Set<String> keys(String pattern) {
        Log.debug("RedisUtil: Fuzzy matching key ! pattern: [{}]", pattern);
        try {
            AssertUtil.isNotNull(pattern, "RedisUtil: The pattern is null !");
            return redis.keys(pattern);
        } catch (Exception ex) {
            Log.error("RedisUtil: Fuzzy matching key exception ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 将当前数据库的key移动到给定的数据库中
     *
     * @param key Key
     * @param db  目标DB索引
     * @return java.lang.Boolean
     * @author 孔明
     * @date 2019-12-10 15:53:03
     */
    public Boolean move(String key, int db) {
        Log.debug("RedisUtil: Move key[{}] to [{}] db !", key, db);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            return redis.move(key, db);
        } catch (Exception ex) {
            Log.error("RedisUtil: Move key to other db exception ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 移除key的过期时间,key将永久保存
     *
     * @param key Key
     * @return java.lang.Boolean
     * @author 孔明
     * @date 2019-12-10 15:56:57
     */
    public Boolean persist(String key) {
        Log.debug("RedisUtil: Remove the expiration time of the key[{}]! ", key);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            return redis.persist(key);
        } catch (Exception ex) {
            Log.error("RedisUtil: Remove the expiration time exception of the key ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 以秒为单位返回 key 的剩余的过期时间。
     *
     * @param key Key
     * @return java.lang.Long
     * @author 孔明
     * @date 2019-12-10 16:00:51
     */
    public Long getExpire(String key) {
        Log.debug("RedisUtil: Return the expiration time of key[{}] !", key);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            return redis.getExpire(key);
        } catch (Exception ex) {
            Log.error("RedisUtil: Return the expiration time exception of key ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 获取给定key的剩余存活时间,并根据timeUnit转换成指定格式
     *
     * @param key      Key
     * @param timeUnit 返回格式
     * @return java.lang.Long
     * @author 孔明
     * @date 2019-12-10 16:04:42
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        Log.debug("RedisUtil: Get the time to live for key[{}]", key);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            AssertUtil.isNotNull(timeUnit, "RedisUtil: The TimeUnit is null !");
            return redis.getExpire(key, timeUnit);
        } catch (Exception ex) {
            Log.error("RedisUtil: Get the keys live time exception ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 随机返回一个key
     *
     * @return java.lang.String
     * @author 孔明
     * @date 2019-12-10 16:09:39
     */
    public String randomKey() {
        Log.debug("RedisUtil: Get a key at random !");
        try {
            return redis.randomKey();
        } catch (Exception ex) {
            Log.error("RedisUtil: Get a key exception at random ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 重命名Key的名称
     *
     * @param key    Key
     * @param newKey 新名称
     * @return void
     * @author 孔明
     * @date 2019-12-10 16:13:27
     */
    public void rename(String key, String newKey) {
        Log.debug("RedisUtil: Rename the key[{}] ; new key[{}] !", key, newKey);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            AssertUtil.isNotNull(newKey, "RedisUtil: The newKey is null !");
            redis.rename(key, newKey);
        } catch (Exception ex) {
            Log.error("RedisUtil: Rename the key exception ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 当newkey不存在时,将key改名为newkey
     *
     * @param key    Key
     * @param newKey newKey
     * @return java.lang.Boolean
     * @author 孔明
     * @date 2019-12-10 16:16:09
     */
    public Boolean renameNX(String key, String newKey) {
        Log.debug("RedisUtil: Rename the key[{}} if new key[{}] not exist !", key, newKey);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            AssertUtil.isNotNull(newKey, "RedisUtil: The newKey is null !");
            return redis.renameIfAbsent(key, newKey);
        } catch (Exception ex) {
            Log.error("RedisUtil: Rename the key exception ! ", ex);
            throw new UtilException(ex);
        }
    }

    /**
     * 返回 key 所储存的值的类型。
     *
     * @param key Key
     * @return org.springframework.data.redis.connection.DataType
     * @author 孔明
     * @date 2019-12-10 16:19:07
     */
    public DataType type(String key) {
        Log.debug("RedisUtil: Returns the type of value stored by key[{}] !", key);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: The key is null !");
            return redis.type(key);
        } catch (Exception ex) {
            Log.error("RedisUtil: Returns the type of value stored by key exception ! ", ex);
            throw new UtilException(ex);
        }
    }

    // ==============================================  String命令 ==============================================

    /**
     * 设置指定KEY的值
     *
     * @param key   KEY
     * @param value 值
     * @return java.lang.Boolean
     * @author 孔明
     * @date 2019-12-16 10:15:17
     */
    public Boolean set(String key, Object value) {
        Log.debug("RedisUtil: 设置指定KEY的值! key[{}] , value[{}]", key, value);
        try {
            AssertUtil.isNotNull(key, "RedisUtil : 设置指定KEY的值异常 -> key is null !");
            AssertUtil.isNotNull(value, "RedisUtil: 设置指定KEY的值异常 ->  value is null !");
            redis.opsForValue().set(key, value);
            return Boolean.TRUE;
        } catch (Exception ex) {
            Log.error("RedisUtil: 设置指定KEY的值异常 key[{}] , value[{}] -> {} !", key, value, ex);
            return Boolean.FALSE;
        }
    }

    /**
     * 设置指定KEY的值和过期时间
     *
     * @param key     KEY
     * @param value   值
     * @param milli 过期时间(单位秒)
     * @return java.lang.Boolean
     * @author 孔明
     * @date 2019-12-16 16:15:36
     */
    public Boolean set(String key, Object value, long milli) {
        Log.debug("RedisUtil: 设置指定KEY的值! key[{}] , value[{}], seconds:[{}]", key, value, milli);
        try {
            AssertUtil.isNotNull(key, "RedisUtil : 设置指定KEY的值异常 -> key is null !");
            AssertUtil.isNotNull(value, "RedisUtil: 设置指定KEY的值异常 ->  value is null !");
            AssertUtil.isTrue(milli > 0, "RedisUtil: 设置指定KEY的值异常 ->  seconds 小于或等于零 !");
            redis.opsForValue().set(key, value, milli, TimeUnit.MILLISECONDS);
            return Boolean.TRUE;
        } catch (Exception ex) {
            Log.error("RedisUtil: 设置指定KEY的值异常 key[{}] , value[{}], seconds:[{}] -> {} !", key, value, milli, ex);
            return Boolean.FALSE;
        }
    }

    /**
     * 获取指定Key的值!
     *
     * @param key Key
     * @return T
     * @author 孔明
     * @date 2019-12-16 10:18:57
     */
    public <T> T get(String key) {
        Log.debug("RedisUtil : 获取指定Key的值! Key:{}", key);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: 获取指定Key的值 -> Key is null !");
            return (T) redis.opsForValue().get(key);
        } catch (Exception ex) {
            Log.error("RedisUtil: 获取指定Key[{}]的值异常 -> {}", key, ex);
            return null;
        }
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key   KEY
     * @param start 开始位置
     * @param end   结束位置
     * @return java.lang.String
     * @author 孔明
     * @date 2019-12-16 10:28:58
     * @description redis> SET greeting "hello, my friend"
     * OK
     * <p>
     * redis> GETRANGE greeting 0 4          # 返回索引0-4的字符，包括4。
     * "hello"
     * <p>
     * redis> GETRANGE greeting -1 -5        # 不支持回绕操作
     * ""
     * <p>
     * redis> GETRANGE greeting -3 -1        # 负数索引
     * "end"
     * <p>
     * redis> GETRANGE greeting 0 -1         # 从第一个到最后一个
     * "hello, my friend"
     * <p>
     * redis> GETRANGE greeting 0 1008611    # 值域范围不超过实际字符串，超过部分自动被符略
     * "hello, my friend"
     */
    public String getRange(String key, long start, long end) {
        Log.debug("RedisUtil: 获取Key对应值的指定范围的子字符串! key:[{}] , start:[{}] , end:[{}]", key, start, end);
        try {
            AssertUtil.isNotNull(key, "RedisUtil: 获取Key对应值指定范围的字符串异常: -> Key is null !");
            return redis.opsForValue().get(key, start, end);
        } catch (Exception ex) {
            Log.error("RedisUtil: 获取Key对应值指定范围的字符串异常 key:[{}] , start:[{}] , end:[{}] ->{}", key, start, end, ex);
            return null;
        }
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     *
     * @param key   KEY
     * @param value 值
     * @return T 返回类型
     * @author 孔明
     * @date 2019-12-16 10:36:13
     */
    public <T> T getAndSet(String key, Object value) {
        Log.debug("RedisUtil: 设置Key的值为value,并返回旧值! KEY:[{}] , value:[{}]", key, value);
        try {
            AssertUtil.isNotNull(key, "设置Key的值为value,并返回旧值异常 -> Key is null !");
            return (T) redis.opsForValue().getAndSet(key, value);
        } catch (Exception ex) {
            Log.error("RedisUtil: 设置Key值为value,并返回旧值异常 KEY:[{}] , value:[{}] -> {}", key, value, ex);
            return null;
        }
    }

    /**
     * 将 key 所储存的值加上增量 increment 。<br/>
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     *
     * @param key       KEY
     * @param increment 增量
     * @return java.lang.Long
     * @author 孔明
     * @date 2019-12-17 17:26:10
     */
    public Long increment(String key, long increment) {
        Log.debug("RedisUtil: 将key[{}]所储存的值加上增量increment[{}]", key, increment);
        try {
            AssertUtil.isNotNull(key, "将key所储存的值加上增量increment异常 -> Key is null !");
            return redis.opsForValue().increment(key, increment);
        } catch (Exception ex) {
            Log.error("RedisUtil: 将key[{}]所储存的值加上增量increment[{}]异常 ->{} ", key, increment, ex);
            return null;
        }
    }
}
