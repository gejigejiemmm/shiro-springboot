package cn.edu.zzuli.shirospringboot.mapper;

import cn.edu.zzuli.shirospringboot.bean.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper {

    @Select("SELECT p.* FROM user u \n" +
            "JOIN user_role ur ON u.id = ur.userId\n" +
            "JOIN role r ON ur.roleId = r.id\n" +
            "JOIN role_permission rp ON rp.roleId = r.id\n" +
            "JOIN permission p ON p.id = rp.permissionId\n" +
            "WHERE u.username = #{username}")
    List<Permission> getPermissionsByUserName(String username);
}
