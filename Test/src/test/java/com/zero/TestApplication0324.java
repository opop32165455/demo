package com.zero;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.zero.dto.DemoData;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.asm.Type;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.Serializable;
import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestApplication0324 {


    @Test
    public void testTime() {
        final List<String> strings = FileUtil.readLines("0331/id_to_term.txt", Charset.defaultCharset());


        final Integer[] i = {0};
        final List<String> list = strings.stream()
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());
        ListUtil.partition(list, 1000).forEach(list1 -> {
                    i[0] = i[0] + 1;
                    final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                    ListUtil.partition(list1, 1000)
                            .forEach(ids -> {
                                queryBuilder.should(QueryBuilders.termsQuery("post_id", ids));
                                System.out.println("ids = " + ids);
                            });
                    String bool = "{\"query\": " + queryBuilder.toString() + "}";
                    final String replace = bool.replace("\"", "\\\"");
                    final String replace2 = replace;
                    final String replace3 = StrUtil.removeAll(replace2, "\r\n");
                    final String replace4 = StrUtil.removeAll(replace3, "\n");
                    final String replace5 = StrUtil.removeAll(replace4, "\r");
                    final String replace6 = StrUtil.removeAll(replace5, " ");
                    FileUtil.writeString("elasticdump --input=http://10.11.205.5:9200/intelligence_social_like_user_hash_* --output=post_like_0" + i[0] + ".json --searchBody=\"" + replace6+"\"", new File("D:\\system-info\\桌面\\bool_like\\" + "like-bool-" + i[0] + ".sh"), Charset.defaultCharset());
                    System.out.println("queryBuilder = " + queryBuilder);
                }
        );


    }

    @Test
    public void testApp() {
        var string = FileUtil.readString("0331/account.json", Charset.defaultCharset());


        final AccountResp accountResp = new Gson().fromJson(string, AccountResp.class);


        ExcelWriter excelWriter = ExcelUtil.getWriter("D:\\system-info\\桌面\\tmp\\" + "107tw账号-" + System.currentTimeMillis() + ".xlsx");

        final List<Series> list = accountResp.getData().list;
        excelWriter.writeRow(CollUtil.newArrayList("账号id", "名字", "主页链接", "简介", "发帖数", "关注数", "被关注数", "点赞数", "标签"), true);
        for (Series user : list) {
            excelWriter.writeRow(CollUtil.newArrayList(user.uid,
                    user.name,
                    user.homepage,
                    user.desc,
                    user.postNum,
                    user.following, user.follower, user.likeNum, user.label.toString()), true);
        }


        //  writer.close();
        excelWriter.close();


    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountResp {
        Integer code;
        String msg;
        Data1 data;
    }

    public static class Data1 {
        List<Series> list;

    }

    @Data
    public static class Series {
        String uid;
        String name;
        String homepage;
        String desc;
        Long postNum;

        @SerializedName("friendNum")
        Long following;

        @SerializedName("followerNum")
        Long follower;

        Long likeNum;

        List<String> label;


    }

}
