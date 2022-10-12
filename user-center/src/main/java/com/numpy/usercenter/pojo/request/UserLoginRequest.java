package com.numpy.usercenter.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*
用户登录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginRequest implements Serializable {
    private  static final long serialVersionUID = 1212122;
    private String userAccount;
    private String userPassword;

}
