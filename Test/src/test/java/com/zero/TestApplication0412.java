package com.zero;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.zero.TestApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestApplication0412 {


    @Test
    public void testTime() {
        final List<String> strings = FileUtil.readLines("0412/tw.txt", Charset.defaultCharset());


        final Integer[] i = {0};
        final List<String> list = strings.stream()
                .filter(StrUtil::isNotBlank)
                .distinct()
                .collect(Collectors.toList());

        ListUtil.partition(list, 5000).forEach(list1 -> {
                    i[0] = i[0] + 1;
                    final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                    ListUtil.partition(list1, 1000)
                            .forEach(ids -> {
                                queryBuilder.should(QueryBuilders.termsQuery("user_id.keyword", ids));
                                System.out.println("ids = " + ids);
                            });
                    String bool = "{\"_source\":{\"includes\":[\"id\",\"screen_name\",\"nickname\",\"user_type\",\"following_count\",\"url\",\"a_category\",\"description\",\"verified\",\"location\",\"lang\",\"friends_count\"]}," +
                            "\"query\": " + queryBuilder + "}";
                    final String replace2 = bool.replace("\"", "\\\"");
                    final String replace3 = StrUtil.removeAll(replace2, "\r\n");
                    final String replace4 = StrUtil.removeAll(replace3, "\n");
                    final String replace5 = StrUtil.removeAll(replace4, "\r");
                    final String replace6 = StrUtil.removeAll(replace5, " ");
                    String[] esUrls = {"10.11.205.5:9200", "10.11.205.6:9200", "10.11.205.7:9200"};

                    FileUtil.writeString("elasticdump --input=http://" + esUrls[i[0] % 2] + "/intelligence_social_user_v1 --output=tw_user_0" + i[0] + ".json --searchBody=\"" + replace6 + "\"", new File("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0412\\"+"0412/tw_user_search_" + i[0] + ".sh"), Charset.defaultCharset());
                    System.out.println("queryBuilder = " + queryBuilder);
                }
        );


    }

    @Test
    public void testApp() {
        var string = FileUtil.readString("0331/account.json", Charset.defaultCharset());


        //final AccountResp accountResp = new Gson().fromJson(string, AccountResp.class);
        //
        //
        //ExcelWriter excelWriter = ExcelUtil.getWriter("D:\\system-info\\桌面\\tmp\\" + "107tw账号-" + System.currentTimeMillis() + ".xlsx");
        //
        //final List<Series> list = accountResp.getData().list;
        //excelWriter.writeRow(CollUtil.newArrayList("账号id", "名字", "主页链接", "简介", "发帖数", "关注数", "被关注数", "点赞数", "标签"), true);
        //for (Series user : list) {
        //    excelWriter.writeRow(CollUtil.newArrayList(user.uid,
        //            user.name,
        //            user.homepage,
        //            user.desc,
        //            user.postNum,
        //            user.following, user.follower, user.likeNum, user.label.toString()), true);
        //}


        //  writer.close();
        //excelWriter.close();


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
