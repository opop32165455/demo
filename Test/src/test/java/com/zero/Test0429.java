package com.zero;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.charset.Charset;
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
public class Test0429 {

    @Test
    public void testApp() {
        List<String> list = FileUtil.readLines("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0429\\7904_account_id_friend_id.txt", Charset.defaultCharset());

        HashSet<String> set = new HashSet<>(list);
        File file = FileUtil.writeLines(set, new File("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0429\\7904_account_id_friend_id_distinct.txt"),
                Charset.defaultCharset());
        System.out.println("file = " + file);
    }

    @Test
    public void QB() {
        List<String> list = FileUtil.readLines("0429/7904_account_id_set.txt", Charset.defaultCharset());


        final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        ListUtil.partition(list, 1000)
                .forEach(ids -> {
                    queryBuilder.should(QueryBuilders.termsQuery("user_id.keyword", ids));
                    System.out.println("ids = " + ids);
                });
        System.out.println("queryBuilder = " + queryBuilder);


    }
}
