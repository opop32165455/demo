package com.fromZero.zero;


import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import com.fromZero.zeroShiro.ZeroShiroApplication;
import com.fromZero.zeroShiro.dao.SysPermissionDao;
import com.fromZero.zeroShiro.dao.SysRoleDao;
import com.fromZero.zeroShiro.dao.SysUserDao;
import com.fromZero.zeroShiro.entity.SysPermission;
import com.fromZero.zeroShiro.entity.SysRole;
import com.fromZero.zeroShiro.entity.SysUser;
import com.fromZero.zeroShiro.shiro.service.FilterChainDefinitionService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(classes = ZeroShiroApplication.class)
@RunWith(SpringRunner.class)
public class ZeroBeginningApplicationTests {

    @Resource
    SysPermissionDao sysPermissionDao;
    @Resource
    FilterChainDefinitionService filterChainDefinitionService;
    @Resource
    SysUserDao sysUserDao;
    @Resource
    SysRoleDao sysRoleDao;

    @Test
    public void contextLoads() {

        //List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        //
        //integers.stream().map(i -> {
        //            if (i < 5){
        //                System.out.println("if"+i);
        //            }
        //            return 9;
        //        }
        //).forEach(System.out::println);

        //  System.out.println(StrUtil.replace(test, "\"", "\'"));


        // System.out.println("1");
        StringBuffer stringBuffer = new StringBuffer("");
        List<SysPermission> sysPermissions = sysPermissionDao.selectList(null);
        ///app/neo4j/** = login,roleOrFilter[test1, lytest, dev, System Manager]
        for (SysPermission sysPermission : sysPermissions) {
            String url = sysPermission.getUrl();
            List<String> roleList = sysPermissionDao.selectRoleByPermissionId(sysPermission.getId());
            String rolesString = roleList.toString();
            stringBuffer.append(url).append("=").append(rolesString).append("\r\n");

        }
        //返回 url1=login,oleOrFilter,权限名1 /r/n url2=login,oleOrFilter,权限名2 ...
        System.out.println(stringBuffer.toString());
    }

    @Test
    public void testLoadIni() {
        String fileName = "shiro_auth.ini";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        Ini ini = new Ini();
        try {
            ini.load(inputStream);
        } catch (Exception e) {
            System.out.println(fileName);
        }
        String section = "base_auth";
        Set<String> keys = ini.get(section).keySet();
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            String value = ini.addSection(section).get(key);
            sb.append(key).append(" = ")
                    .append(value).append("\r\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void testLoadtxt() {
        String fileName = "classpath:shiro_auth.txt";
        StringBuffer sb = new StringBuffer();
        try {
            //读取文件
            String source = ResourceUtil.readUtf8Str(fileName);
            List<String> info = Arrays.asList(StrUtil.split(source, "\r\n"));
            info.stream().filter(String -> !StrUtil.startWith(String, "#")
                    && !StrUtil.startWith(String, "//")
                    && StrUtil.isNotBlank(String))
                    .map(string -> string + "\r\n")
                    .forEach(string -> sb.append(string));
        } catch (Exception e) {
            System.out.println(fileName);
        }
        System.out.println(sb.toString());
    }


    @Test
    public void testChain() {
        filterChainDefinitionService.loadFilterChainDefinitions();


    }

    @Test
    public void testUserDao() {
        Set<String> roles = new LinkedHashSet<>();
       // Set<String> permissions = new LinkedHashSet<>();
        //查询该用户信息
        SysUser sysUser = sysUserDao.selectUserPermissionsByEmail("abc");
        //分配权限
        Set<String> permissions = sysUser.getRoles()
                .stream()
                //添加角色信息
                .peek(role -> roles.add(role.getName()))
                .map(SysRole::getPermissions)
                //.map(set -> set.stream().peek(permission -> permissions.add(permission.getName())))
                .flatMap(a -> a.stream().map(SysPermission::getName))
                .collect(Collectors.toSet());
        System.out.println("roles = " + roles);
        System.out.println("permissions=" + permissions);


    }

    @Test
    public void MD5_match() {
        //md5 加盐  散列次数
        Md5Hash md5Hash = new Md5Hash("abc","salt",9981);
        String password = md5Hash.toHex();
        System.out.println("password = " + password);
        //md5 加盐  散列次数
        Md5Hash md5Hash2 = new Md5Hash("yuanqie","salt",9981);
        String password2 = md5Hash2.toHex();
        System.out.println("password = " + password2);
        //md5 加盐  散列次数
        Md5Hash md5Hash3 = new Md5Hash("miaowazhongzi","salt",9981);
        String password3 = md5Hash3.toHex();
        System.out.println("password = " + password3);
        //md5 加盐  散列次数
        Md5Hash md5Hash4 = new Md5Hash("huoqiushu","salt",9981);
        String password4 = md5Hash4.toHex();
        System.out.println("password = " + password4);
    }
}
