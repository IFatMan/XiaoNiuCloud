package cn.xiaoniu.cloud.server.auto.config.rules;

import com.baomidou.mybatisplus.toolkit.StringUtils;


public enum NamingStrategy {
    nochange,


    underline_to_camel;


    public static String underlineToCamel(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }
        String tempName = name;

        if (StringUtils.isCapitalMode(name) || StringUtils.isMixedMode(name)) {
            tempName = name.toLowerCase();
        }
        StringBuilder result = new StringBuilder();

        String[] camels = tempName.split("_");
        for (String camel : camels) {

            if (!StringUtils.isEmpty(camel)) {


                if (result.length() == 0) {

                    result.append(camel);
                } else {

                    result.append(capitalFirst(camel));
                }
            }
        }
        return result.toString();
    }


    public static String removePrefix(String name, String... prefix) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }
        if (null != prefix) {
            for (String pf : prefix) {
                if (name.toLowerCase().matches("^" + pf.toLowerCase() + ".*")) {

                    return name.substring(pf.length());
                }
            }
        }
        return name;
    }


    public static boolean isPrefixContained(String name, String... prefix) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }
        if (null != prefix) {
            for (String pf : prefix) {
                if (name.toLowerCase().matches("^" + pf.toLowerCase() + ".*")) {
                    return true;
                }
            }
        }
        return false;
    }


    public static String removePrefixAndCamel(String name, String[] tablePrefix) {
        return underlineToCamel(removePrefix(name, tablePrefix));
    }


    public static String capitalFirst(String name) {
        if (StringUtils.isNotEmpty(name)) {
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return "";
    }
}
