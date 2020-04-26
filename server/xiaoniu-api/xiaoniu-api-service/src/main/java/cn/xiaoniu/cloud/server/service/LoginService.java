package cn.xiaoniu.cloud.server.service;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.digest.MD5;
import cn.xiaoniu.cloud.server.api.dao.db.UserAutoDao;
import cn.xiaoniu.cloud.server.api.model.po.User;
import cn.xiaoniu.cloud.server.util.JsonUtil;
import cn.xiaoniu.cloud.server.util.context.CacheCustomer;
import cn.xiaoniu.cloud.server.util.id.IdUtil;
import cn.xiaoniu.cloud.server.web.authority.AuthorityUtil;
import cn.xiaoniu.cloud.server.web.entity.EntityUtil;
import cn.xiaoniu.cloud.server.web.redis.RedisDataSourceHolder;
import cn.xiaoniu.cloud.server.web.response.Result;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import cn.xiaoniu.cloud.server.web.util.Log;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    private UserAutoDao userDao;
    //登录
    public Result login(String account, String psd) {

        User user = new User();
        user.setAccount(account);
        user = userDao.findOne(user);
        if(Objects.isNull(user)) {
            return Result.fail(ResultStatus.ERROR_REQUEST , "用户不存在！");
        }
        String password = user.getPassword();
        String s = MD5.create().digestHex(psd);
        if(!password.equals(s)){
            return Result.fail(ResultStatus.ERROR_REQUEST , "密码不正确！");
        }

        CacheCustomer cacheCustomer = new CacheCustomer();
        cacheCustomer.setAccount(account);
        cacheCustomer.setId(user.getId());
        List<String> permission = Lists.newArrayList(account);
        cacheCustomer.setPermissions(permission);
        // 3. 生成Token
        String token = IdUtil.getUUID();
        // 4. 以token为Key，将缓存对象缓存到Redis
        RedisDataSourceHolder.execute(redisUtil -> redisUtil.set(token, cacheCustomer));

        user.setToken(token);
        userDao.updateEntityById(user);
        return Result.success(userDao.findOne(user));
    }

    //注册
    public Result register(String name,String account, String pwd,String againpwd) {
        if(!pwd.equals(againpwd)){
            return Result.fail(ResultStatus.ERROR_REQUEST , "两次密码不一致！");
        }

        List<User> userList = userDao.findByFieldName("account", account);
        if(CollUtil.isEmpty(userList)){
            return Result.fail(ResultStatus.ERROR_REQUEST , "账号已存在！");
        }

        User user = EntityUtil.newEntity(User.class);
        user.setAccount(account);
        user.setName(name);
        user.setPassword(MD5.create().digestHex(pwd));
        userDao.saveEntity(user);
        return Result.success(user);
    }


    //修改密码
    public Result updatePwd(String oldpwd,String pwd) {
        CacheCustomer customer = AuthorityUtil.getCurrCustomer();
        if(Objects.isNull(customer)) {
            return null;
        }
        User  user = userDao.findById(customer.getId());
        if(Objects.isNull(user)) {
            return Result.fail(ResultStatus.ERROR_REQUEST , "用户不存在！");
        }

        String password = user.getPassword();
        String s = MD5.create().digestHex(oldpwd);
        if(!password.equals(s)){
            return Result.fail(ResultStatus.ERROR_REQUEST , "密码不正确！");
        }
        user.setPassword(MD5.create().digestHex(pwd));
        userDao.updateEntityById(user);
        return Result.success();
    }

}
