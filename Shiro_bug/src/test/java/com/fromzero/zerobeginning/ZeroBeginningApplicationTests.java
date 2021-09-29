package com.fromzero.zerobeginning;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = ZeroShiroApplication.class)
@RunWith(SpringRunner.class)
public class ZeroBeginningApplicationTests {

    @Test
    public void contextLoads() {

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        integers.stream().map(i -> {
                    if (i < 5){
                        System.out.println("if"+i);
                    }
                    return 9;
                }
        ).forEach(System.out::println);

      //  System.out.println(StrUtil.replace(test, "\"", "\'"));


        // System.out.println("1");
    }

}
