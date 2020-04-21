package cn.xiaoniu.cloud.server.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

/**
 * JSON 转换工具类
 */
public class JsonUtil {

    private static final Gson GSON = new Gson();

    /**
     * 对象 转为 字符串类型
     *
     * @param object
     * @return
     */
    public static String toJson(final Object object) {
        return GSON.toJson(object);
    }

    /**
     * 字符串转对象
     *
     * @param jsonStr
     * @param clazz
     * @return T
     * @author 穷先生
     */
    public static <T> T fromJson(final String jsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        return GSON.fromJson(jsonStr, clazz);
    }

    /**
     * 通过 key 获取值
     *
     * @param jsonStr
     * @param key
     * @return
     */
    public static String getValueByKey(final String jsonStr, final String key) {
        if (StringUtils.isBlank(jsonStr) || StringUtils.isBlank(key)) {
            return null;
        }
        JsonObject jsonObject = GSON.fromJson(jsonStr, JsonObject.class);
        if (!jsonObject.has(key)) {
            return null;
        }
        return jsonObject.get(key).getAsString();
    }


    /**
     * 是否是Json字符串
     *
     * @param jsonStr Json字符串
     * @return
     */
    public static boolean isJson(String jsonStr) {
        JsonElement jsonElement;
        try {
            jsonElement = GSON.toJsonTree(jsonStr);
        } catch (Exception e) {
            return false;
        }
        return jsonElement != null && jsonElement.isJsonObject();
    }
}
