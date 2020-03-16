package cn.edu.zzuli.shirospringboot;

import cn.edu.zzuli.shirospringboot.bean.User;
import cn.edu.zzuli.shirospringboot.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        User halo = userMapper.getUserByName("halo");
        System.out.println(halo);
    }

}
