package cn.xiaoniu.cloud.server.util.operator;

import cn.xiaoniu.cloud.server.util.AssertUtil;

/**
 * 运算工具类
 *
 * @author 孔明
 * @date 2020/4/22 15:58
 * @description cn.xiaoniu.cloud.server.util.OperatorUtil
 */
public final class OperatorUtil {

    private OperatorUtil() {
    }

    /**
     * 类似于三母运算符
     *
     * @param condition 条件
     * @param trueExe   当condition == true 时执行
     * @param falseExe  当condition == false 时执行
     * @return 执行结果
     */
    public static void ternaryOperator(boolean condition, Operator trueExe, Operator falseExe) {
        AssertUtil.isNotNull(trueExe, "参数trueExe不能为Null！");
        AssertUtil.isNotNull(falseExe, "参数falseExe不能为Null！");
        if (condition) {
            trueExe.accept();
        } else {
            falseExe.accept();
        }
    }
}
