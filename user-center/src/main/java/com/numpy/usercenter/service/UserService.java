package com.numpy.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.numpy.usercenter.pojo.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author 大饼干
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2022-09-27 17:23:22
*/
@Service
public interface UserService extends IService<User> {
    long userRegister(String userAccount, String userPassword, String checkpassword);



    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @return 返回脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest);

}
