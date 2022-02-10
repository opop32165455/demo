package com.zero;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestApplicationTests {


    @Test
    public void testMap() {
        HashMap<String, String> map = MapUtil.newHashMap();
        map.put("1", "2");

        String s = map.get("version");
        System.out.println("s = " + s);


    }

    @Test
    public void integer_test() {

        var writer = CsvUtil.getWriter("D:\\system-info\\桌面\\nebula调研\\test.csv", CharsetUtil.CHARSET_UTF_8);

        var names = CollUtil.newArrayList("amini_85",
                "EricVolat",
                "AlertyQ",
                "mcktestssss",
                "馃嚠馃嚪Mazandaran of Iran馃嚠馃嚪郾",
                "鄄郯",
                "丕賵賳噩丕 馃憠",
                "yamthrsfavnigga",
                "kaylaxsamantha",
                "Mersad__75",
                "VjcImtqoQfS3lcd",
                "Yahaidar265",
                "khamenei_ir",
                "JohnLilburne4",
                "jsnider31",
                "amini_85",
                "EricVolat",
                "AlertyQ",
                "mcktestssss",
                "馃嚠馃嚪Mazandaran of Iran馃嚠馃嚪郾:鄄郯",
                "丕賵賳噩丕 馃憠");

        var events = CollUtil.newArrayList(
                "event_1003",
                "event_1004",
                "event_1005",
                "event_1006"
        );

        var writeData = IntStream.range(1, 10 * 10000)
                .mapToObj(i -> new String[]{
                        "Involve_0" + i,
                        "Twitter" + RandomUtil.randomInt(0, 100 * 10000),
                        events.get(RandomUtil.randomInt(0, 3))
                })
                .collect(Collectors.toList());


        writer.write(writeData);
    }

    @Test
    public void read() {
        var reader = CsvUtil.getReader();
        var data = reader.read(new File("D:\\system-info\\桌面\\nebula调研\\线上数据\\事件1相关的KOL账号.csv"), CharsetUtil.CHARSET_UTF_8);
        for (CsvRow datum : data) {
            System.out.println("datum.toString() = " + datum.toString());
        }

        var writer = CsvUtil.getWriter("D:\\system-info\\桌面\\nebula调研\\线上数据\\账号.csv", CharsetUtil.CHARSET_UTF_8);
        writer.write();
    }
    @Builder
    static class SysUser {
        Integer id;
        String email;
    }

}
