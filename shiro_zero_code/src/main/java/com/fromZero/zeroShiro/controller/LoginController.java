package com.fromZero.zeroShiro.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.fromZero.zeroShiro.entity.SysUser;
import com.fromZero.zeroShiro.shiro.service.ShiroTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author R4441
 * @Desciption: 登陆接口
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/2/002 22:00
 */
@Slf4j
@RestController
@RequestMapping
public class LoginController extends ApiController {
    @Resource
    private ShiroTokenService tokenService;

    /**
     * 登陆
     *
     * @param user 登陆用户信息 （用email 和 password 登陆）
     * @param rememberMe 是否记住密码（默认记住）
     * @return 登入结果
     */
    @GetMapping("/login")
    public String login(SysUser user, @RequestParam(required = false,defaultValue ="true")String rememberMe) {
        if (StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getPassword())) {
          return "请输入用户名和密码！";
        }
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
           Boolean isRememberMe ="true".equals(rememberMe);
            tokenService.login(user, isRememberMe);
            // subject.checkRole("admin");
         //   subject.checkPermissions("query", "add");
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
           // return "用户名不存在！";
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
           // return "账号或密码错误！";
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
          //  return "没有权限";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login success!";
    }

    /**
     * 登出
     * @return 结果
     */
    @GetMapping("/logout")
    public String login(){
        tokenService.logout();
        return "logout success";
    }

    /**
     * 权限注解校验 url比较少的情况可以使用
     *
     * @return
     */
    @RequiresRoles("admin")
    @GetMapping("/admin")
    public String admin() {
        return "admin success!";
    }
    /**
     * 权限注解校验 url比较少的情况可以使用
     *
     * @return
     */
    @RequiresPermissions("query")
    @GetMapping("/query")
    public String query() {
        return "query success!";
    }
    /**
     * 权限注解校验 url比较少的情况可以使用
     *
     * @return
     */
    @RequiresPermissions("add")
    @GetMapping("/add")
    public String add() {
        return "add success!";
    }

    @GetMapping("/index")
    public String index() {
        return "index success!";
    }
}
