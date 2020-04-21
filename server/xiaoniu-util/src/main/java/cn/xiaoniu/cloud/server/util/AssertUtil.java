package cn.xiaoniu.cloud.server.util;

import cn.xiaoniu.cloud.server.util.character.StringUtil;
import cn.xiaoniu.cloud.server.util.exception.UtilException;
import lombok.experimental.UtilityClass;

/**
 * @author 孔明
 * @date 2019/12/10 14:58
 * @description cn.xiaoniu.cloud.server.util.AssertUtil
 */
@UtilityClass
public class AssertUtil {

    public void isTrue(Boolean expression, String msg) {
        if (Boolean.FALSE.equals(expression)) {
            UtilException.throwe(msg);
        }
    }

    public void isNotNull(Object object, String msg) {
        if (object == null) {
            UtilException.throwe(msg);
        }
    }

    public void isNotNull(String text, String msg) {
        if (StringUtil.isBlank(text)) {
            UtilException.throwe(msg);
        }
    }

}
