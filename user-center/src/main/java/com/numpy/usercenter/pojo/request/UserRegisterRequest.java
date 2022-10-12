package com.numpy.usercenter.pojo.request;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户注册请求体
 * 这里也可不实现序列化，但是涉及传输的，最好实现一下序列化接口
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegisterRequest implements Serializable {
    private  static final long serialVersionUID = 42L;
    private String userAccount;
    private String userPassword;
    private String checkpassword;




}
