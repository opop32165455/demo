package com.zero;


import cn.hutool.core.map.MapUtil;
import com.threads.ZeroApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@SpringBootTest(classes = ZeroApplication.class)
@RunWith(SpringRunner.class)
public class ZeroApplicationTests {

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
