package com.fromZero.zeroShiro.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.fromZero.zeroShiro.entity.SysUser;
import com.fromZero.zeroShiro.shiro.service.ShiroTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
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
     * @param user       登陆用户信息 （用email 和 password 登陆）
     * @param rememberMe 是否记住密码（默认记住）
     * @return 登入结果
     */
    @GetMapping("/login")
    public String login(SysUser user, @RequestParam(required = false, defaultValue = "true") String rememberMe) {
        if (StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getPassword())) {
            return "请输入用户名和密码！";
        }
        //进行验证，这里可以捕获异常，然后返回对应信息
        Boolean isRememberMe = "true".equals(rememberMe);
        tokenService.login(user, isRememberMe);
        Subject subject = SecurityUtils.getSubject();
        //校验是否有是该角色
        //subject.checkRole("simple_guy");

        //subject.hasRole("superManager");


        //校验是否有该权限
        //subject.checkPermissions("browse", "insert");

        //subject.isPermitted("light_saber:test1");

        //subject.isPermitted("good_saber:drive:eagle5");

        return "login success!";
    }

    /**
     * 登出
     *
     * @return 结果
     */
    @GetMapping("/logout")
    public String login() {
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
