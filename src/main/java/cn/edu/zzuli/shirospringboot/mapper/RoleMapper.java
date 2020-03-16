package cn.edu.zzuli.shirospringboot.mapper;

import cn.edu.zzuli.shirospringboot.bean.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select("SELECT r.* FROM user u \n" +
            "JOIN  user_role ur ON u.id = ur.userId\n" +
            "JOIN role r ON ur.roleId = r.id\n" +
            "WHERE u.username = #{username}")
    List<Role> getRolesByUserName(String username);
}
