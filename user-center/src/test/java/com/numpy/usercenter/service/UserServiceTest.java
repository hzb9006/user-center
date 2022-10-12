package com.numpy.usercenter.service;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.numpy.usercenter.mapper.UserMapper;
import com.numpy.usercenter.pojo.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 */
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("阿标");
        user.setUserAccount("731209574");
        user.setAvatarUrl("C:\\Users\\大饼干\\Desktop\\身份证\\skystar2.jpg");
        user.setGender(1);
        user.setUserPassword("12345678");
        user.setPhone("123");
        user.setEmail("456");

        /*
        save:插入一条记录（选择字段，策略插入）
        形参:
            entity – 实体对象
         */
        boolean result = userService.save(user);
        System.out.println(user.getId());//mybatis-plus 会自动获取新增的主键
        assertTrue(result);// 断言，判断两个值是否相等


    }

    @Test
    void userRegister() {
        String userAccount="dameng12";
        String userPassword="123456";
        String checkPassword="123456";

        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount="da";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

    }
    @Test
    void userlogin(){
        String userAccount="123";
        String userPassword="123456";

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",userPassword);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);


    }
}