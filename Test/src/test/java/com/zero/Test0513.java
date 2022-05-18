package com.zero;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import com.sun.org.apache.regexp.internal.RE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.var;
import org.junit.Test;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangxuecheng4441
 * @date 2022/5/13/013 11:20
 */
public class Test0513 {
    @Test
    public void testUser() {
        var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0513\\蒙独标签数据\\fb_user.csv", Charset.defaultCharset());
        List<String> posts = FileUtil.readLines(new File("D:\\system-info\\下载\\test_hbase_user_fb_meng_du_0513_1119.txt"), Charset.defaultCharset());

        List<UserTmp> userTmpList = posts.stream()
                .filter(s -> !"0".equals(s.trim()))
                .map(s->JSON.parseObject(s,UserTmp.class)).collect(Collectors.toList());

        ArrayList<String> fieldName = new ArrayList<>();
        for (Field declaredField : UserTmp.class.getDeclaredFields()) {
            declaredField.setAccessible(true);
            fieldName.add(declaredField.getName());
        }
        writer.write(ArrayUtil.toArray(fieldName, String.class));

        for (UserTmp userTmp : userTmpList) {
            Map<String, Object> map = BeanUtil.beanToMap(userTmp);

            ArrayList<String> tmp = new ArrayList<>();
            for (String field : fieldName) {
                tmp.add(String.valueOf(map.getOrDefault(field, "")));
            }

            writer.write(ArrayUtil.toArray(tmp, String.class));
        }

        writer.close();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UserTmp implements Serializable {
        @JSONField(name = "id")
        @Flag
        private String user_id;
        @JSONField(name = "screen_name")
        @Flag
        private String user_name;
        @JSONField(name = "nickname")
        @Flag
        private String nick_name;
        @JSONField(name = "url")
        @Flag
        private String home_url;
        @JSONField(name = "gender")
        @Flag
        private String sex;
        @JSONField(name = "contact")
        @Flag
        private String contact;
        @JSONField(name = "url2")
        @Flag
        private String website;
        @JSONField(name = "location")
        @Flag
        private String places_lived;
        @JSONField(name = "hometown")
        @Flag
        private String home_town;
        @JSONField(name = "work")
        @Flag
        private String work;
        @JSONField(name = "educations")
        @Flag
        private String education;
        @JSONField(name = "relationship_status")
        @Flag
        private String relationship;
        @JSONField(name = "family_members")
        @Flag
        private String family;
        @JSONField(name = "desc")
        @Flag
        private String signature;
        @JSONField(name = "quotes")
        @Flag
        private String quotes;
        @JSONField(name = "friends_count")
        @Flag
        private String friend_cnt;
        @JSONField(name = "like_count")
        @Flag
        private String like_cnt;
        @JSONField(name = "about_contents")
        @Flag
        private String about_contents;
        @JSONField(name = "profile_image_url")
        @Flag
        private String profile;
        @JSONField(name = "profile_image_url2")
        @Flag
        private String banner;
    }

    @Test
    public void testPost() {
        List<String> posts = FileUtil.readLines(new File("D:\\system-info\\下载\\status_fb_meng_du_0513_1635_num_3.txt"), Charset.defaultCharset());

        Map<String, List<PostTmp>> postMap = posts.stream()
                .filter(s -> !"0".equals(s.trim()))
                .map(s -> JSON.parseObject(s, PostTmp.class))
                .peek(post -> {
                    String content = post.getContent();
                    if ("{\"standard\":\"\"}".equals(content)) {
                        String shareUrl = Optional.ofNullable(post.attachments)
                                .map(PostTmp.Attachments::getData)
                                .map(data -> {
                                            List<String> urls = data.stream()
                                                    .map(PostTmp.AttachmentsData::getMedia)
                                                    .filter(Objects::nonNull)
                                                    .map(s->s.getString("source"))
                                                    .collect(Collectors.toList());
                                            return CollUtil.join(urls, ",");
                                        }
                                )
                                .orElse("");
                        post.setContent(shareUrl);
                    }

                })
                .collect(Collectors.groupingBy(PostTmp::getUser_id));

        for (Map.Entry<String, List<PostTmp>> postEn : postMap.entrySet()) {
            try (var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0513\\蒙独标签数据\\fb_post_" + postEn.getKey() + ".csv", Charset.defaultCharset());) {
                ArrayList<String> fieldName = new ArrayList<>();
                for (Field declaredField : PostTmp.class.getDeclaredFields()) {
                    declaredField.setAccessible(true);
                    if (declaredField.getAnnotation(Flag.class) != null) {
                        fieldName.add(declaredField.getName());
                    }
                }
                String[] name = ArrayUtil.toArray(fieldName, String.class);
                writer.write(name);

                List<PostTmp> esTmpList = postEn.getValue();

                esTmpList.stream()
                        .peek(source -> {
                            source.setForward(Optional.ofNullable(source.getRepost_status()).map(Test0510.PostTmp.RePostTmp::getUrl).orElse(""));
                            source.setMood_cnt(source.laugh_count + source.angry_count + source.like_count + source.laugh_count);
                            source.setUser_url(source.post_url.split("/post/")[0]);
                        })
                        .map(BeanUtil::beanToMap)
                        .forEach(map -> {
                            ArrayList<String> data = new ArrayList<>();
                            for (String name1 : name) {
                                Object orDefault = map.getOrDefault(name1, "");
                                data.add(String.valueOf(orDefault));
                            }
                            writer.write(ArrayUtil.toArray(data, String.class));
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EsTmp implements Serializable {


        PostTmp _source;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class FriendsTmp {
            String friend_id;
            String user_id;
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class PostTmp implements Serializable {
        @JSONField(name = "user_url")
        @Flag
        private String user_url;
        @JSONField(name = "user_id")
        @Flag
        private String user_id;
        @JSONField(name = "id")
        @Flag
        private String post_id;
        @JSONField(name = "author")
        @Flag
        private String author;
        @JSONField(name = "url")
        @Flag
        private String post_url;
        @JSONField(name = "forward")
        @Flag
        private String forward;
        @JSONField(name = "created_at")
        @Flag
        private String date;
        @JSONField(name = "content")
        @Flag
        private String content;
        @JSONField(name = "mood_cnt")
        @Flag
        private String mood_cnt;
        @JSONField(name = "reply_count")
        @Flag
        private String comment_cnt;
        @JSONField(name = "retweet_count")
        @Flag
        private String forward_cnt;

        private String laugh_count;
        private String angry_count;
        private String like_count;
        private String sad_count;
        private Attachments attachments;
        private Test0510.PostTmp.RePostTmp repost_status;


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class Attachments {
            List<AttachmentsData> data;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class AttachmentsData {
            JSONObject media;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class RePostTmp {
            String url;
        }
    }


}
