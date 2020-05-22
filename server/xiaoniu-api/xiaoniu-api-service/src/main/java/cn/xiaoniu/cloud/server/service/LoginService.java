package cn.xiaoniu.cloud.server.service;

import cn.hutool.core.collection.CollUtil;
import cn.xiaoniu.cloud.server.api.common.CacheUser;
import cn.xiaoniu.cloud.server.api.dao.cache.LoginDao;
import cn.xiaoniu.cloud.server.api.dao.db.DirectoryAutoDao;
import cn.xiaoniu.cloud.server.api.dao.db.UserAutoDao;
import cn.xiaoniu.cloud.server.api.model.po.Directory;
import cn.xiaoniu.cloud.server.api.model.po.User;
import cn.xiaoniu.cloud.server.util.context.CacheCustomer;
import cn.xiaoniu.cloud.server.util.id.IdUtil;
import cn.xiaoniu.cloud.server.web.authority.AuthorityUtil;
import cn.xiaoniu.cloud.server.web.entity.EntityUtil;
import cn.xiaoniu.cloud.server.web.exception.RequestParamException;
import cn.xiaoniu.cloud.server.web.response.Result;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    private DirectoryAutoDao directoryAutoDao;

    @Autowired
    private UserAutoDao userDao;

    @Autowired
    private LoginDao loginDao;

    /**
     * 登录
     *
     * @param account
     * @param pwd
     * @return
     */
    @Transactional
    public Result<CacheUser> login(String account, String pwd) {
        // 1. 验证账户名密码
        User user = findUserByAccountOrPrimary(account, null);
        String password = user.getPassword();
        if (!password.equals(pwd)) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "账户或密码不正确！");
        }

        // 2. 生成用户缓存信息
        CacheUser cacheUser = CacheUser.convertFromUser(user, Lists.newArrayList());

        // 3. 将之前token置位失效
        if (Objects.nonNull(user.getToken())) {
            loginDao.delCacheUser(user.getToken());
        }

        // 4. 根目录ID
        Directory directory = EntityUtil.select(Directory.class);
        directory.setUserId(cacheUser.getId());
        directory.setParentId(Directory.Constant.ROOT_PARENT_ID);
        directory = directoryAutoDao.findLastOne(directory);
        cacheUser.setRootDirectoryId(directory.getId());

        // 4. 生成Token,以token为Key，将缓存对象缓存到Redis
        String token = IdUtil.getUUID();
        cacheUser.setToken(token);
        loginDao.saveCacheUser(token, cacheUser);

        // 5. 将本次Token持久化到数据库
        User updateUser = EntityUtil.updateByPrimary(user);
        updateUser.setToken(token);
        userDao.updateEntityById(updateUser);

        return Result.success(cacheUser);
    }

    /**
     * 注册
     *
     * @param name
     * @param account
     * @param pwd
     * @return
     */
    @Transactional
    public Result register(String name, String account, String pwd) {
        List<User> userList = userDao.findByFieldName("account", account);
        if (CollUtil.isNotEmpty(userList)) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "账号已存在！");
        }

        // 创建用户信息
        User user = EntityUtil.newEntity(User.class);
        user.setAccount(account);
        user.setName(name);
        user.setPassword(pwd);
        userDao.saveEntity(user);

        // 创建用户根目录
        Directory directory = EntityUtil.newEntity(Directory.class);
        directory.setUserId(user.getId());
        directory.setType(Directory.Type.FOLDER);
        directory.setName(Directory.Constant.ROOT_NAME);
        directory.setParentId(Directory.Constant.ROOT_PARENT_ID);
        directory.setLevel(Directory.Constant.ROOT_LEVEL);
        directory.setLeftNo(Directory.Constant.LEFT);
        directory.setRightNo(Directory.Constant.RIGHT);
        directoryAutoDao.saveEntity(directory);

        return Result.success();
    }


    /**
     * 修改密码
     *
     * @param oldPwd 原密码
     * @param newPwd 新密码
     * @return
     */
    @Transactional
    public Result updatePwd(String oldPwd, String newPwd) {
        CacheCustomer customer = AuthorityUtil.getCurrCustomer();
        User user = userDao.findById(customer.getId());
        if (Objects.isNull(user)) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "用户不存在！");
        }

        if (!user.getPassword().equals(oldPwd)) {
            return Result.fail(ResultStatus.ERROR_REQUEST, "密码不正确！");
        }

        user.setPassword(newPwd);
        userDao.updateEntityById(user);
        return Result.success();
    }

    /**
     * 通过账户或者用户ID获取用户信息
     *
     * @param account 账户
     * @param id      用户ID
     * @return 用户信息
     */
    private User findUserByAccountOrPrimary(String account, Long id) {
        User user = null;
        if (StringUtils.isNotBlank(account)) {
            user = userDao.findLastOne(EntityUtil.select(User.class).setAccount(account));
        } else if (Objects.nonNull(id)) {
            user = userDao.findById(id);
        }

        if (Objects.isNull(user)) {
            throw new RequestParamException("账户不存在");
        }
        return user;
    }

}
