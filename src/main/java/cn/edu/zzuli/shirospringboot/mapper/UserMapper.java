package cn.edu.zzuli.shirospringboot.mapper;

import cn.edu.zzuli.shirospringboot.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from users where username = #{username}")
    User getUserByName(String username);
}
