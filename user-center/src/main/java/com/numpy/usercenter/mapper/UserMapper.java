package com.numpy.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.numpy.usercenter.pojo.User;
import org.apache.ibatis.annotations.Mapper;


/**
* @author 大饼干
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2022-09-27 17:23:22
* @Entity generator.domain.User
*/

@Mapper
public interface UserMapper extends BaseMapper<User> {

}




