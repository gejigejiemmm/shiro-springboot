package cn.edu.zzuli.shirospringboot.controller;

import cn.edu.zzuli.shirospringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //可以设置记住我
        //token.setRememberMe(true);

        try {
            //执行登录方法，如果没有异常说明登录成功。
            //实际上 shiro 会自动帮我们去调用 Realm doGetAuthenticationInfo()
            subject.login(token);
            return "index";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg","用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg","密码错误");
            return "login";
        }


    }

    @RequestMapping("/noAuth")
    @ResponseBody
    public String noAuthorized() {
        return "未经授权无法访问该页面";
    }

    @RequiresRoles(value = "root")
    @RequestMapping("/rootTest")
    @ResponseBody
    public String rootTest() {
        return "root";
    }

    @RequiresRoles(value = "user")
    @RequestMapping("/userTest")
    @ResponseBody
    public String userTest() {
        return "user";
    }

}
