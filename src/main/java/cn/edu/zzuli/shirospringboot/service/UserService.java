package cn.edu.zzuli.shirospringboot.service;

import cn.edu.zzuli.shirospringboot.bean.User;
import cn.edu.zzuli.shirospringboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User getUser(String username) {
        return userMapper.getUserByName(username);
    }
}
