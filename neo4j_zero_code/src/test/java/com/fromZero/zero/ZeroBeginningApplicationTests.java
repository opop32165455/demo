package com.fromZero.zero;


import cn.hutool.core.map.MapUtil;
import com.fromZero.neo4jZero.Neo4jApplication;
import com.fromZero.neo4jZero.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest(classes = Neo4jApplication.class)
@RunWith(SpringRunner.class)
public class ZeroBeginningApplicationTests {

    @Resource
    SysUserService sysUserService;

    @Test
    public void contextLoads() {

        // String esIndex = "test_index-v9";
        //
        // String[] split = StrUtil.split(esIndex,"-v");
        //Integer newVersion =  Integer.parseInt(split[1])+1;
        // esIndex=split[0]+"-v"+newVersion;
        // System.out.println("newVersion = " + esIndex);

        boolean removeById = sysUserService.removeById(9623);
        System.out.println("removeById = " + removeById);


    }

    @Test
    public void testMap() {
        HashMap<String, String> map = MapUtil.newHashMap();
        map.put("1", "2");

        String s = map.get("version");
        System.out.println("s = " + s);


    }

    @Test
    public void integer_test() {

        Object a2 = 725808960;
        if (a2 instanceof Long) {
            System.out.println(123);
            System.out.println(123);
            System.out.println(123);
            System.out.println(123);
        }

    }


}
