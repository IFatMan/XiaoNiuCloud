[TOC]

项目介绍
========

在构建Web项目时会有许多通用配置，比如CORS跨域、Redis多数据源……
此项目的作用便是将这些配置简化，让他们更容易被使用。主要使用了Spring的AOP功能

模块介绍
========

| 模块名称 | 目录                                  | 简介               |
|----------|---------------------------------------|--------------------|
| 权限验证 | cn.xiaoniu.cloud.server.web.authority | 登录权限、接口权限 |
| CORS     | cn.xiaoniu.cloud.server.web.cors      | 跨域               |
| DB       | cn.xiaoniu.cloud.server.web.db        | Druid框架配置      |
| POJO     | cn.xiaoniu.cloud.server.web.entity    | POJO基类及创建工具 |
| 异常     | cn.xiaoniu.cloud.server.web.exception | 常用异常定义       |
| 日志     | cn.xiaoniu.cloud.server.web.log       | 请求日志打印       |
| redis    | cn.xiaoniu.cloud.server.web.redis     | Redis多数据源      |
| response | cn.xiaoniu.cloud.server.web.reponse   | 请求返回基类       |
| swagger2 | cn.xiaoniu.cloud.server.web.swagger   | 在线文档工具       |

模块使用介绍
============

权限验证
--------

权限验证分为登录权限验证、接口权限验证

### 登录权限验证使用

#### 简介：

此功能需要\@Login、LoginAspect.class、LoginCacheDao.class配合使用

#### 返回代码说明：

1.  接口返回代码6001，表示未登录，即Request Header 中没有令牌

2.  接口返回代码6002，表示登录已失效，即令牌过期

#### 使用步骤：

**第一步：** 通过登录接口登录成功后，将令牌加载到缓存
```java
    /**
     * @author 孔明
     * @date 2020/4/21 16:20
     * @description cn.xiaoniu.cloud.server.api.controller.LoginController
     */
    @Api("登录接口")
    @RestController
    public class LoginController {
    
        @PutMapping("/login")
        @RedisSource("api")
        @ApiOperation("登录接口")
        public Result login(@RequestParam("account") String account, @HideData @RequestParam("password") String password) {
            // 1。验证账户密码
            // 省略.......
    
            // 2. 创建缓存对象
            CacheCustomer cacheCustomer = new CacheCustomer();
            List<String> permission = Lists.newArrayList("ABABC");
            cacheCustomer.setPermissions(permission);
    
            // 3. 生成Token
            String token = "DDD";
    
            // 4. 以token为Key，将缓存对象缓存到Redis
            RedisDataSourceHolder.execute(redisUtil -> redisUtil.set("DDD", cacheCustomer));
    
            // 5. 返回令牌
            return Result.success();
        }
    
        @Permission("ABABC")
        @GetMapping("/testPermission")
        @ApiOperation("测试接口")
        public Result testPermission() {
            return Result.success();
        }
    
        @Login
        @GetMapping("/testLoginPermission")
        @ApiOperation("测试接口")
        public Result testLoginPermission() {
            return Result.success();
        }
    
    }
```
**第二步：** 配置LoginAspect.class
```java
    /**
     * @author 孔明
     * @date 2020/4/23 11:52
     * @description cn.xiaoniu.cloud.server.api.config.ImportConfig
     */
    @Component
    @Import({LoginAspect.class})
    public class ImportConfig {
    }
```
**第三步：** 在application.properties中配置登录令牌在Request Header中的名称
```properties
    # 登录令牌在Request Header中的名称
    authority.login-token-name=AccessToken
```
**第四步：** 在需要登录权限的接口标注\@Login注解
```java
    @Login
    @GetMapping("/testLoginPermission")
    @ApiOperation("测试接口")
    public Result testLoginPermission() {
        return Result.success();
    }
```
**第五步：** 实现LoginCacheDao接口

说明： LoginCacheDao接口全路径是cn.xiaoniu.cloud.server.web.authority.
LoginCacheDao，作用是通过getCacheCustomer方法取出用户详细信息
```java
    /**
     * @author 孔明
     * @date 2020/4/24 15:56
     * @description cn.xiaoniu.cloud.server.api.dao.cache.LoginDao
     */
    @Component
    public class LoginDao implements LoginCacheDao {
    
        @Override
        @RedisSource("api")
        public CacheCustomer getCacheCustomer(String accessToken) {
            return RedisDataSourceHolder.execute(redisUtil -> redisUtil.get(accessToken));
        }
    }
```
**第六步：** 权限验证

<div style="float: left;">
    <img width="150" height="150" src="https://github.com/qionggit/XiaoNiuCloud/blob/master/server/xiaoniu-document/sources/images/a.png" /> 
    <img width="150" height="150" src="https://github.com/qionggit/XiaoNiuCloud/blob/master/server/xiaoniu-document/sources/images/b.png" />
    <img width="150" height="150" src="https://github.com/qionggit/XiaoNiuCloud/blob/master/server/xiaoniu-document/sources/images/c.png" />
</div>

接口权限验证使用
----------------

接口使用者，必定已经登录，即接口使用了\@Permission注解，便不需要使用\@Login注解

#### 返回代码使用
 接口返回代码6004，表示没有接口权限
 
#### 使用步骤：

**第一步：** 配置PermissionAspect.class
```java
    /**
     * @author 孔明
     * @date 2020/4/23 11:52
     * @description cn.xiaoniu.cloud.server.api.config.ImportConfig
     */
    @Component
    @Import({PermissionAspect.class})
    public class ImportConfig {
    }
```

**第二步：** 接口使用@Permission
```java 
   @Permission("ABABC")
   @GetMapping("/testPermission")
   @ApiOperation("测试接口")
   public Result testPermission() {
       return Result.success();
   }
```

**第三步：** 功能测试
<div style="float: left;">
    <img width="150" height="150" src="https://github.com/qionggit/XiaoNiuCloud/blob/master/server/xiaoniu-document/sources/images/d.jpg" /> 
    <img width="150" height="150" src="https://github.com/qionggit/XiaoNiuCloud/blob/master/server/xiaoniu-document/sources/images/e.jpg" />
</div>

CORS
--------
 CORS跨域功能
 
### 使用步骤
**第一步：** 配置CorsConfig.class
```java
    /**
     * @author 孔明
     * @date 2020/4/23 11:52
     * @description cn.xiaoniu.cloud.server.api.config.ImportConfig
     */
    @Component
    @Import({CorsConfig.class})
    public class ImportConfig {
    }
```

**第二步：** 配置跨域参数
```properties
    # =================== CORS ===================
    # 是否允许Cookies跨域
    cors.allowed-credential=true
    # 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
    cors.max-age=1800
    # 允许跨域路径
    cors.path=/**
    # 允许域名访问
    cors.allowed-origin[0]=*
    # 允许请求头访问
    cors.allowed-header[0]=*
    # 允许提交的Http方法
    cors.allowed-method[0]=GET
    cors.allowed-method[1]=PUT
    cors.allowed-method[2]=POST
    cors.allowed-method[3]=DELETE
```

DB 之 Druid
--------
 Druid配置，包括连接池配置，监控页面配置
 
### 使用步骤
**第一步：** 配置DruidConfig.class
```java
    /**
     * @author 孔明
     * @date 2020/4/23 11:52
     * @description cn.xiaoniu.cloud.server.api.config.ImportConfig
     */
    @Component
    @Import({DruidConfig.class})
    public class ImportConfig {
    }
```

**第三步：** 配置参数

备注：详细配置信息请参考Druid文档
```properties
    # =================== 数据源配置 ===================
    spring.datasource.name=XiaoNiuCloudServer
    spring.datasource.url=
    spring.datasource.username=
    spring.datasource.password=
    spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.filters=stat,wall
    spring.datasource.maxActive=20
    spring.datasource.initialSize=5
    spring.datasource.maxWait=60000
    spring.datasource.minIdle=1
    spring.datasource.timeBetweenEvictionRunsMillis=60000
    spring.datasource.minEvictableIdleTimeMillis=300000
    spring.datasource.validationQuery=select 'x'
    spring.datasource.testWhileIdle=true
    spring.datasource.testOnBorrow=false
    spring.datasource.testOnReturn=false
    spring.datasource.poolPreparedStatements=true
    spring.datasource.maxOpenPreparedStatements=20
    
    # 添加IP白名单
    spring.datasource.init-param.servlet.allow=127.0.0.1
    # 添加IP黑名单，黑名单优先级更高
    #spring.datasource.init-param.servlet.deny=127.0.0.1
    # 添加控制台管理用户名称
    spring.datasource.init-param.servlet.loginUsername=
    # 添加控制台管理用户密码
    spring.datasource.init-param.servlet.loginPassword=
    # 是否能够重置数据
    spring.datasource.init-param.servlet.resetEnable=false
    # 拦截器路径过滤规则
    spring.datasource.init-param.filter-patterns=/*
    # 拦截器路径忽略过滤格式
    spring.datasource.init-param.filter.exclusions=*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,
```

POJO
------
详细信息可参考源码

异常
------
详细信息可参考源码

日志
-------
如果使用了@Login或者@Permission可不 使用@PrintLog
### 使用步骤：
**第一步：** 配置PrintLotAspect.
```java 
    /**
     * @author 孔明
     * @date 2020/4/23 11:52
     * @description cn.xiaoniu.cloud.server.api.config.ImportConfig
     */
    @Component
    @Import({PrintLog.class})
    public class ImportConfig {
    }
```

**第二步：** @PrintLog , @HideData使用

@HideData 可隐藏参数值不在日志中打印
```java
    @PutMapping("/login")
    @RedisSource("api")
    @PrintLog("登录接口")
    @ApiOperation("登录接口")
    public Result login(@RequestParam("account") String account, @HideData @RequestParam("password") String password) {
        // 1。验证账户密码
        // 省略.......

        // 2. 创建缓存对象
        CacheCustomer cacheCustomer = new CacheCustomer();
        List<String> permission = Lists.newArrayList("ABABC");
        cacheCustomer.setPermissions(permission);

        // 3. 生成Token
        String token = "DDD";

        // 4. 以token为Key，将缓存对象缓存到Redis
        RedisDataSourceHolder.execute(redisUtil -> redisUtil.set("DDD", cacheCustomer));

        // 5. 返回令牌
        return Result.success();
    }
```

**第四部：** 打印结果
```
2020-04-25 20:23:29.620  INFO 2968 --- [nio-8080-exec-7] c.x.cloud.server.web.log.PrintLogAspect  : -------------------- 请求日志 --------------------
2020-04-25 20:23:29.624  INFO 2968 --- [nio-8080-exec-7] c.x.cloud.server.web.log.PrintLogAspect  : 请求IP:192.168.2.1
2020-04-25 20:23:29.625  INFO 2968 --- [nio-8080-exec-7] c.x.cloud.server.web.log.PrintLogAspect  : 请求地址:/login
2020-04-25 20:23:29.627  INFO 2968 --- [nio-8080-exec-7] c.x.cloud.server.web.log.PrintLogAspect  : 请求方式:PUT
2020-04-25 20:23:29.628  INFO 2968 --- [nio-8080-exec-7] c.x.cloud.server.web.log.PrintLogAspect  : 请求方法:登录接口
2020-04-25 20:23:29.630  INFO 2968 --- [nio-8080-exec-7] c.x.cloud.server.web.log.PrintLogAspect  : 请求方法路径:cn.xiaoniu.cloud.server.api.controller.LoginController.login()
2020-04-25 20:23:29.658  INFO 2968 --- [nio-8080-exec-7] c.x.cloud.server.web.log.PrintLogAspect  : 请求参数:{"account":"123","password":"*****"}
2020-04-25 20:23:29.659 DEBUG 2968 --- [nio-8080-exec-7] c.x.c.s.web.redis.RedisSourceAspect      : 当前Redis数据源切换至：api
2020-04-25 20:23:29.669 DEBUG 2968 --- [nio-8080-exec-7] c.x.cloud.server.web.redis.RedisUtil     : RedisUtil: 设置指定KEY的值! key[DDD] , value[cn.xiaoniu.cloud.server.util.context.CacheCustomer@638979c3]
2020-04-25 20:23:29.919  INFO 2968 --- [nio-8080-exec-7] io.lettuce.core.EpollProvider            : Starting without optional epoll library
2020-04-25 20:23:29.923  INFO 2968 --- [nio-8080-exec-7] io.lettuce.core.KqueueProvider           : Starting without optional kqueue library
2020-04-25 20:23:30.439  INFO 2968 --- [nio-8080-exec-7] c.x.cloud.server.web.log.PrintLogAspect  : 请求返回:{"status":2000,"msg":"SUCCESS"}
2020-04-25 20:23:30.439  INFO 2968 --- [nio-8080-exec-7] c.x.cloud.server.web.log.PrintLogAspect  : 请求耗时:826
```

redis
-------
支持多数据源配置
### 使用步骤
**第一步：** 配置RedisSourceAspect.class
```java 
    /**
     * @author 孔明
     * @date 2020/4/23 11:52
     * @description cn.xiaoniu.cloud.server.api.config.ImportConfig
     */
    @Component
    @Import({RedisSourceAspect.class})
    public class ImportConfig {
    }
```

**第二步：** 配置名为**api**的数据源
```properties
    # ================================== REDIS API =======================================
    spring.redis.sources.api.host=39.104.186.167
    spring.redis.sources.api.port=6677
    spring.redis.sources.api.database=6
    spring.redis.sources.api.password=KM@REDIS
    spring.redis.sources.api.timeout.seconds=5
    spring.redis.sources.api.ssl=true
    
    # ================================== REDIS POOL =======================================
    spring.redis.sources.api.lettuce.pool.max-active=20
    spring.redis.sources.api.lettuce.pool.max-wait.seconds=-1
    spring.redis.sources.api.lettuce.pool.max-idle=10
    spring.redis.sources.api.lettuce.pool.min-idle=0
```

**第三步：** 代码中使用
```java
    @RedisSource("api")
    @GetMapping("/testPermission")
    @ApiOperation("测试接口")
    public Result testPermission() {
        RedisDataSourceHolder.execute(redisUtil -> redisUtil.set("DDD", "TEST"));
        return Result.success();
    }
```

response
---------
详细信息可参考源码

swagger2
--------
### 使用步骤
**第一步：** 引入Swagger2Config.class
```java 
    /**
     * @author 孔明
     * @date 2020/4/23 11:52
     * @description cn.xiaoniu.cloud.server.api.config.ImportConfig
     */
    @Component
    @Import({Swagger2Config.class})
    public class ImportConfig {
    }
```

**第二步：** Swagger2 配置
```properties
    # =================== Swagger2 ===================
    swagger2.enable=true
    swagger2.base-package=
    swagger2.title=小牛云盘API接口
    swagger2.description=小牛云盘API接口
    swagger2.version=v1.0
    swagger2.concat-name=孔明
    swagger2.concat-email=
    swagger2.header[0].name=AccessToken
    swagger2.header[0].description=权限Token,登录返回
    swagger2.header[0].required=false
```

**效果图：**

<img width="450" height="300" src="https://github.com/qionggit/XiaoNiuCloud/blob/master/server/xiaoniu-document/sources/images/f.jpg" />
