package com.numpy.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.numpy.usercenter.contant.UserContant;
import com.numpy.usercenter.pojo.User;
import com.numpy.usercenter.pojo.request.UserLoginRequest;
import com.numpy.usercenter.pojo.request.UserRegisterRequest;
import com.numpy.usercenter.service.UserService;
import com.numpy.usercenter.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.numpy.usercenter.contant.UserContant.ADMIN_ROLE;

@RestController// 适用于编写restful风格的api，返回值默认为json类型
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Long UserRegister(@RequestBody UserRegisterRequest userRegisterRequest) {// RequestBody是springmvc的内容
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkpassword = userRegisterRequest.getCheckpassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkpassword)) {
            return null;
        }

        return  userService.userRegister(userAccount, userPassword, checkpassword);


    }

    @PostMapping("/login")
    public User doLogin(@RequestBody UserLoginRequest userLoginRequest,HttpServletRequest request) {// RequestBody是springmvc的内容
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        User user = userService.userLogin(userAccount, userPassword, request);
        return user;


    }

    @GetMapping("/search")// 查询用户
    public List<User> searchUsers(  String username,HttpServletRequest request) {
        //!isAdmin(request): 当不是管理员的时候返回false，所以这里判断取反
        if (!isAdmin(request)){
            return new ArrayList<>();
        }


        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){ // 判断长度是否为0，内容是否为空

            queryWrapper.like("username",username);
        }
        return userService.list(queryWrapper);

    }


    @PostMapping("/delete")
    public boolean deleteUsers(@RequestBody long id,HttpServletRequest request ){
        //!isAdmin(request): 当不是管理员的时候返回false，所以这里判断取反
        if (!isAdmin(request)){
            return false;
        }


        if (id<=0){
            return false;
        }
        return userService.removeById(id);

    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        /**
         * 仅管理员查询:
         * 以下是UserServiceImpl中的代码：
         *   httpServletRequest.getSession().setAttribute(USER_LOGIN_STATE,user);
         *   在注册的时候，以USER_LOGIN_STATE为name，user为值保存了用户的信息，所以这里才可以通过登录态取出用户信息
         */
        Object user = request.getSession().getAttribute(UserContant.USER_LOGIN_STATE);
        User user1=(User)user;
        if (user1==null ||   user1.getUserRole()!=ADMIN_ROLE){
            return false;
        }
        return true;

    }

}




