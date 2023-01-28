package com.zero;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangxuecheng4441
 * @date 2022/4/29/029 15:02
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class Test0501 {

    @Test
    public void testApp() {
        List<String> list = FileUtil.readLines("0501/comment_user_id.txt", Charset.defaultCharset());

        HashSet<String> set = new HashSet<>(list);
        File file = FileUtil.writeLines(set, new File("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0501\\comment_user_id_distinct-0501.txt"),
                Charset.defaultCharset());
        System.out.println("file = " + file);
    }

    @Test
    public void QB() {
        List<String> list = FileUtil.readLines("0501/post_id.txt", Charset.defaultCharset());
        String listString = list.toString();
        System.out.println("list = " + listString);

        List<String> set = list.stream().distinct().collect(Collectors.toList());
        final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        ListUtil.partition(set, 1000)
                .forEach(ids -> {
                    queryBuilder.should(QueryBuilders.termsQuery("post_id", ids));
                    System.out.println("ids = " + ids);
                });
        System.out.println("queryBuilder = " + queryBuilder);


    }

    @Test
    public void KeywordTo100match() {
        String kws = "武漢病毒|中共病毒|武漢肺炎|強封|強檢|毒針|洪門宴|疫苗外交|蝙蝠女";
        List<String> keywords = StrUtil.split(kws, "|");

        final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.filter(QueryBuilders.rangeQuery("created_at").gte("2022-04-20T00:00:00.000+0800").lte("2022-05-09T00:00:00.000+0800"));

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        keywords.forEach(kw -> {
            HashMap<String, Float> fieldMap = new HashMap<String, Float>() {
                {
                    put("content.*", 1.0f);
                }
            };
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(kw)
                    .fields(fieldMap)
                    .minimumShouldMatch("100%")
                    .slop(0)
                    .type(MultiMatchQueryBuilder.Type.PHRASE);
            boolQuery.should(multiMatchQueryBuilder);
        });
        queryBuilder.filter(boolQuery);
        System.out.println("queryBuilder = " + queryBuilder);
    }
}
