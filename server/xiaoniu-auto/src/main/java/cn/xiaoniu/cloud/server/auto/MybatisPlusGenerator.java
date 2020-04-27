package cn.xiaoniu.cloud.server.auto;

import cn.xiaoniu.cloud.server.auto.config.*;
import cn.xiaoniu.cloud.server.auto.config.rules.DbType;
import cn.xiaoniu.cloud.server.auto.config.rules.NamingStrategy;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class MybatisPlusGenerator {

    /**
     * 文件生成目录
     */
    private final static String outPutDir = "E:\\autoGenJava\\";

    private final static String DATABASE_URL = "jdbc:mysql://39.104.186.167:3506/xiaoniu-cloud?useUnicode=true&characterEncoding=UTF8&useSSL=false";

    private final static String DATABASE_NAME="root";

    private final static String DATABASE_PASSWORD="KongMing8@";

    /**
     * 基础包名
     */
    private final static String basePackage = "cn.xiaoniu.cloud.server.api";
    private final static boolean genAllTables = false;
    private final static String superEntityClass = "cn.xiaoniu.cloud.server.web.entity.BaseEntity";
    private final static String[] tables = {"t_directory"};
    private final static boolean isController = false;
    private final static boolean isService = false;
    private final static boolean isEntity = true;
    private final static boolean isDao = true;
    private final static boolean isDaoMapper = true;
    private final static boolean isVO = false;

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(MybatisPlusGenerator.globalConfig());
        mpg.setDataSource(MybatisPlusGenerator.dataSourceConfig());
        mpg.setStrategy(MybatisPlusGenerator.strategyConfig());
        mpg.setPackageInfo(MybatisPlusGenerator.packageConfig());
        mpg.setCfg(MybatisPlusGenerator.injectionConfig());
        mpg.setTemplate(MybatisPlusGenerator.templateConfig());
        mpg.execute();
    }


    public static PackageConfig packageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setOutUseParentPackage(false);
        packageConfig.setParent(basePackage);
        packageConfig.setController("controller");
        packageConfig.setEntity("model.po");
        packageConfig.setMapper("dao.db");
        packageConfig.setVo("model.vo");
        return packageConfig;
    }

    /**
     * 全局配置
     */
    public static GlobalConfig globalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outPutDir);            //配置路径
        gc.setOpen(false);            //是否打开生成的文件夹
        gc.setAuthor("auto generator");            //author
        gc.setServiceName("%sService");        //设置Service接口后缀
        gc.setServiceImplName("%sServiceImpl");    //设置Service实现类后缀
        gc.setControllerName("%sController");    //设置controller类后缀
        gc.setMapperName("%sAutoDao");
        gc.setXmlName("%sAutoDao");
        gc.setVoName("%sAutoVO");
        gc.setFileOverride(true);                    // 是否覆盖同名文件，默认是false
        gc.setActiveRecord(false);                    // 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);                    // XML 二级缓存
        gc.setBaseResultMap(true);                    // XML ResultMap
        gc.setBaseColumnList(false);                // XML columList
        return gc;
    }

    /**
     * 数据源配置
     */
    public static DataSourceConfig dataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl(DATABASE_URL)
                .setUsername(DATABASE_NAME)
                .setPassword(DATABASE_PASSWORD);
        return dsc;
    }

    /**
     * 策略配置
     */
    public static StrategyConfig strategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);    // 表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setRestControllerStyle(true);   //设置controller自动加RestController注解
        strategy.setDbColumnUnderline(true);
        strategy.setTablePrefix("t_");
        if (genAllTables) {
            strategy.setExclude("t_base");
        } else {
            strategy.setInclude(tables);
        }
        strategy.setSuperEntityClass(superEntityClass);
        strategy.setSuperEntityColumns("id", "del", "create_time", "update_time");
        return strategy;
    }

    /**
     * 自定义配置
     */
    public static InjectionConfig injectionConfig() {
        return new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
    }

    /**
     * 模板配置
     */
    public static TemplateConfig templateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        if (!isController)
            templateConfig.setController(null);
        if (!isService) {
            templateConfig.setService(null);
            templateConfig.setServiceImpl(null);
        }
        if (!isEntity)
            templateConfig.setEntity(null);
        if (!isDao)
            templateConfig.setMapper(null);
        if (!isDaoMapper)
            templateConfig.setXml(null);
        if (!isVO)
            templateConfig.setVo(null);
        return templateConfig;
    }

}