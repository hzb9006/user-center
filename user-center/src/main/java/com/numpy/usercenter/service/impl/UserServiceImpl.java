package com.numpy.usercenter.service.impl;
import java.util.Date;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.numpy.usercenter.mapper.UserMapper;
import com.numpy.usercenter.pojo.User;
import com.numpy.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static com.numpy.usercenter.contant.UserContant.USER_LOGIN_STATE;

/**
* @author 大饼干
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2022-09-27 17:23:22
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;
   private static final String salt="dameng";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkpassword) {
        // 1.校验:为了避免要写很多判断，使用引入的工具apache.commons.lang3.StringUtils; 进行校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkpassword)){
            return -1;//如果为空，返回-1

        }
        if (userAccount.length()<4){
            return -1;
        }
        if (userPassword.length()<8 || checkpassword.length()<8){
            return -1;
        }

        // 账户不能重复--使用mp的条件构造器QueryWrapper，后续学一下，可以考虑是否可以通过其他方法
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count >0){
            return -1;
        }

        // 账户不能包含特殊字符--正则表达式，后续实现

        // 密码和校验密码相同
        if (!userPassword.equals(checkpassword)){
            return -1;
        }

        // 2. 加密
        String handlePassword = DigestUtils.md5DigestAsHex((salt+userPassword).getBytes());

        // 3.插入数据
        User user =new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(handlePassword);
        boolean save = this.save(user);//调用继承的ServiceImpl里面封装的方法，实际上底层是调用mapp接口的数据库insert方法
        // 所以对于service层，可以使用save方法，也可使用userMapper层，userMapper.insert(user);
        if (!save){
            return -1;
        }


        return user.getId();


    }


    @Override
    public User userLogin(String userAccount, String userPassword,HttpServletRequest httpServletRequest) {
        // 1.校验:为了避免要写很多判断，使用引入的工具apache.commons.lang3.StringUtils; 进行校验
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;//如果为空，返回-1

        }
        if (userAccount.length()<0){
            return null;
        }
        if (userPassword.length()<4){
            return null;
        }
        // 2. 加密
        String handlePassword = DigestUtils.md5DigestAsHex((salt+userPassword).getBytes());

        // 查询用户是否存在
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",userPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 如果用户不存在
        if (user ==null){
            log.info("user login faild.userAccount cannot match userPassword");
            return null;
        }

        // 3.脱敏--新生成一个对象，只返回允许返回给前端的值
        User safeUser=new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setUserRole(user.getUserRole());

        safeUser.setPhone(user.getPhone());
        safeUser.setEmail(user.getEmail());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());

        // 4.记录用户的登录态
        httpServletRequest.getSession().setAttribute(USER_LOGIN_STATE,user);

        return safeUser;
        }


}




