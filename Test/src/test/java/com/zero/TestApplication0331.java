package com.zero;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.google.gson.Gson;
import com.zero.TestApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.HttpCookie;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestApplication0331 {


    @Test
    public void testApp() {

    }

    @AllArgsConstructor
    public static enum SourceNum {
        Facebook(6),
        Twitter(3),
        Youtube(8),
        Ins(9),
        Forum(1);
        Integer num;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AggResp {
        String source;
        Data1 data;
    }

    public static class Data1 {
        List<Series> series;

    }

    @Data
    public static class Series {
        String dataName;
        List<Long> data;

    }

}
