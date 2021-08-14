package com.fromZero.zeroShiro.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.fromZero.zeroShiro.entity.SysUser;
import com.fromZero.zeroShiro.shiro.service.ShiroTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author R4441
 * @Desciption: 登陆接口
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/2/002 22:00
 */
@Slf4j
@Controller
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
    public String login(SysUser user,
                        @RequestParam(value = "rememberMe", required = false, defaultValue = "false") String rememberMe,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        if (StringUtils.isEmpty(user.getEmail()) && StringUtils.isEmpty(user.getPassword())) {
            return "beforeLogin/login.html";
        }
        if (StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getPassword())) {
            return "请输入用户名和密码！";
        }
        //进行验证，这里可以捕获异常，然后返回对应信息
        Boolean isRememberMe = "on".equals(rememberMe);
        SysUser login = tokenService.login(user, isRememberMe);
        Subject subject = SecurityUtils.getSubject();
        //校验是否有是该角色
        //jdkObservable.checkRole("simple_guy");

        //jdkObservable.hasRole("superManager");


        //校验是否有该权限
        //jdkObservable.checkPermissions("browse", "insert");

        //jdkObservable.isPermitted("light_saber:test1");

        //jdkObservable.isPermitted("good_saber:drive:eagle5");
        //SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        // 登录前url
        //savedRequest.getRequestUrl();
        // 取得url之后对SavedRequest进行清空
        // 如果未使用接口方式，可以直接跳转url并清空，使用WebUtils中的redirectToSavedRequest方法
        //WebUtils.getAndClearSavedRequest(request);
        return "afterLogin/index.html";

    }

    /**
     * 登出
     *
     * @return 结果
     */
    @GetMapping("/logout")
    public String logout() {
        tokenService.logout();
        return "logout success";
    }


}
