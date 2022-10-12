package com.numpy.usercenter;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {


    @Test
    void contextLoads() {
        String s = DigestUtils.md5DigestAsHex("abcd".getBytes());
        System.out.println(s);

    }

}
