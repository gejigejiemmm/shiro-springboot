package cn.edu.zzuli.shirospringboot.config.realm;

import cn.edu.zzuli.shirospringboot.bean.Permission;
import cn.edu.zzuli.shirospringboot.bean.Role;
import cn.edu.zzuli.shirospringboot.bean.User;
import cn.edu.zzuli.shirospringboot.mapper.PermissionMapper;
import cn.edu.zzuli.shirospringboot.mapper.RoleMapper;
import cn.edu.zzuli.shirospringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权=> doGetAuthorizationInfo");
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        //拿到用户对象（数据库的信息）
        User user = (User) subject.getPrincipal();

        //获取当前用户的角色信息，可能有多个角色
        Set<String> roles=new HashSet<String>();
        List<Role> rolesByUserName = roleMapper.getRolesByUserName(user.getUsername());
        //遍历加入到shiro中，可以使用for循环，这里是java8新特性
        rolesByUserName.forEach(role ->  roles.add(role.getRolename()) );
        info.setRoles(roles);

        //获取权限信息
        List<Permission> permissionsByUserName = permissionMapper.getPermissionsByUserName(user.getUsername());
        permissionsByUserName.forEach(permission -> info.addStringPermission(permission.getPermission()) );

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证=> doGetAuthorizationInfo");
        //获取当前登录的用户
        Subject subject = SecurityUtils.getSubject();

        //从 controller 过来的token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //从数据库中获取当前用户
        User user = userService.getUser(token.getUsername());

        if (user == null) {
            //如果返回 null  shiro会帮我们抛出一个 UnknownAccountException
            //也就是用户不存在
            return null;
        }

        //判断密码 shiro 为了安全，不让我们去碰密码，所以我们需要在 在第二个参数中传入 密码对象
        //SimpleAuthenticationInfo 是 AuthenticationInfo的子类
        //甚至可以md5 加密
        //subject.getPrincipal() 可以获取返回的 第一个值
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
