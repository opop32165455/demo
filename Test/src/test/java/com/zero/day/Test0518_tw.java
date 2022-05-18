package com.zero.day;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.var;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangxuecheng4441
 * @date 2022/5/18/018 14:39
 */
public class Test0518_tw {

    /**
     * tw user
     */
    @Test
    public void testUser() {

        List<String> strings = FileUtil.readLines("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0517\\tw_user.json", Charset.defaultCharset());


        List<UserTmp> userTmpList = strings.stream()
                .filter(s -> !"0".equals(s.trim()))
                .map(s -> JSON.parseObject(s, UserTmp.class)).collect(Collectors.toList());

        ArrayList<String> fieldName = new ArrayList<>();
        for (Field declaredField : UserTmp.class.getDeclaredFields()) {
            declaredField.setAccessible(true);
            fieldName.add(declaredField.getName());
        }


        for (UserTmp userTmp : userTmpList) {
            Map<String, Object> map = BeanUtil.beanToMap(userTmp);

            ArrayList<String> tmp = new ArrayList<>();
            for (String field : fieldName) {
                tmp.add(String.valueOf(map.getOrDefault(field, "")));
            }
            try (var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0517" +
                    "\\tw\\"+"spider_result_Twitter_" + userTmp.username +"\\"
                    + userTmp.username + "_INFO.csv", Charset.defaultCharset());) {
                writer.write(ArrayUtil.toArray(fieldName, String.class));
                writer.write(ArrayUtil.toArray(tmp, String.class));
            }

        }


    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UserTmp implements Serializable {
        @JSONField(name = "id")
        private String id;
        @JSONField(name = "screen_name")
        private String name;
        @JSONField(name = "nickname")
        private String username;
        @JSONField(name = "username_cn")
        private String username_cn;
        @JSONField(name = "label")
        private String label;
        @JSONField(name = "desc")
        private String bio;
        @JSONField(name = "bio_cn")
        private String bio_cn;
        @JSONField(name = "location")
        private String location;
        @JSONField(name = "location_cn")
        private String location_cn;
        @JSONField(name = "url")
        private String url;
        @JSONField(name = "created_at")
        private String join_time;
        @JSONField(name = "statuses_count")
        private String tweets;
        @JSONField(name = "followers_count")
        private String following;
        @JSONField(name = "friends_count")
        private String followers;
        @JSONField(name = "likes")
        private String likes;
        @JSONField(name = "media")
        private String media;
        @JSONField(name = "protected_user")
        private String private_1;
        @JSONField(name = "verified")
        private String verified;
        @JSONField(name = "profile_image_url")
        private String profile_image_url;
        @JSONField(name = "backgroup_image")
        private String backgroup_image;
        @JSONField(name = "capture_user_id")
        private String capture_user_id;
    }
}
