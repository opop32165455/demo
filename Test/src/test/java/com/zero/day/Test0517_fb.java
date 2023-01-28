package com.zero.day;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.zero.Flag;
import com.zero.Test0510;
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
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author zhangxuecheng4441
 * @date 2022/5/13/013 11:20
 */
public class Test0517_fb {
    /**
     * fb用户信息
     */
    @Test
    public void testUser() {

        List<String> strings = FileUtil.readLines(new File("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0517\\fb_user.json"), Charset.defaultCharset());
        List<UserTmp> userTmpList = strings.stream()
                .filter(s -> !"0".equals(s.trim()))
                .map(s -> JSON.parseObject(s, UserTmp.class)).collect(Collectors.toList());
        for (UserTmp userTmp : userTmpList) {
            try (var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\" +
                    "0517\\fb\\spider_result_Facebook_" + userTmp.getNick_name() + "\\" +
                    userTmp.getNick_name() + "_info.csv", Charset.defaultCharset());) {

                ArrayList<String> fieldName = new ArrayList<>();
                for (Field declaredField : UserTmp.class.getDeclaredFields()) {
                    declaredField.setAccessible(true);
                    fieldName.add(declaredField.getName());
                }
                writer.write(ArrayUtil.toArray(fieldName, String.class));

                Map<String, Object> map = BeanUtil.beanToMap(userTmp);

                ArrayList<String> tmp = new ArrayList<>();
                for (String field : fieldName) {
                    tmp.add(String.valueOf(map.getOrDefault(field, "")));
                }

                writer.write(ArrayUtil.toArray(tmp, String.class));

            }
        }


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

    /**
     * 帖子
     */
    @Test
    public void testPost() {
        //json
        //List<String> posts = FileUtil.readLines(new File("D:\\system-info\\下载\\status_fb_meng_du_0513_1635_num_3.txt"), Charset.defaultCharset());
        //
        //Map<String, List<PostTmp>> postMap = posts.stream()
        //        .filter(s -> !"0".equals(s.trim()))
        //        .map(s -> JSON.parseObject(s, PostTmp.class))
        //        .peek(fullText())
        //        .collect(Collectors.groupingBy(PostTmp::getUser_id));

        //array
        String postsArrString = FileUtil.readString("0517/6655/post.json", Charset.defaultCharset());
        List<EsTmp> esTmpList = JSON.parseArray(postsArrString, EsTmp.class);
        Map<String, List<PostTmp>> postMap = esTmpList.stream()
                .map(EsTmp::get_source)
                .peek(fullText())
                .collect(Collectors.groupingBy(PostTmp::getUser_id));

        for (Map.Entry<String, List<PostTmp>> postEn : postMap.entrySet()) {
            try (var writer = CsvUtil.getWriter(new File("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0517\\fb\\" +
                    "spider_result_Facebook_Rebecca Hooper\\Rebecca Hooper_posts.csv"), Charset.defaultCharset());) {
                ArrayList<String> fieldName = new ArrayList<>();
                for (Field declaredField : PostTmp.class.getDeclaredFields()) {
                    declaredField.setAccessible(true);
                    if (declaredField.getAnnotation(Flag.class) != null) {
                        fieldName.add(declaredField.getName());
                    }
                }
                String[] name = ArrayUtil.toArray(fieldName, String.class);
                writer.write(name);

                List<PostTmp> postTmpList = postEn.getValue();

                postTmpList.stream()
                        .peek(source -> {
                            source.setForward(Optional.ofNullable(source.getRepost_status()).map(PostTmp.RePostTmp::getUrl).orElse(""));
                            source.setMood_cnt(source.laugh_count + source.angry_count + source.like_count + source.laugh_count + "");
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

    private Consumer<PostTmp> fullText() {
        return post -> {
            String content = post.getContent();
            if ("{\"standard\":\"\"}".equals(content)) {
                String shareUrl = Optional.ofNullable(post.attachments)
                        .map(PostTmp.Attachments::getData)
                        .map(data -> {
                                    List<String> urls = data.stream()
                                            .map(PostTmp.AttachmentsData::getMedia)
                                            .filter(Objects::nonNull)
                                            .map(s -> s.getString("source"))
                                            .collect(Collectors.toList());
                                    return CollUtil.join(urls, ",");
                                }
                        )
                        .orElse("");
                post.setContent(shareUrl);
            }

        };
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
        @JSONField(name = "created_at_date")
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

        private int laugh_count;
        private int angry_count;
        private int like_count;
        private int sad_count;
        private Attachments attachments;
        private PostTmp.RePostTmp repost_status;


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


    /**
     * fb 分享贴
     */
    @Test
    public void testRePost() {
        try (var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0517\\fb\\" +
                "spider_result_Facebook_Jason Hamilton\\Jason Hamilton_comments.csv", Charset.defaultCharset());) {
            String esJson = FileUtil.readString("0517/6655/post.json", Charset.defaultCharset());
            List<EsTmp4> esTmpList = JSON.parseArray(esJson, EsTmp4.class);


            ArrayList<String> fieldName = new ArrayList<>();
            for (Field declaredField : EsTmp4.PostRelate.class.getDeclaredFields()) {
                declaredField.setAccessible(true);
                if (declaredField.getAnnotation(Flag.class) != null) {
                    fieldName.add(declaredField.getName());
                }
            }
            String[] name = ArrayUtil.toArray(fieldName, String.class);
            writer.write(name);

            esTmpList.stream()
                    .map(EsTmp4::get_source)
                    .peek(source -> {
                        source.setMood_cnt(source.laugh_count + source.angry_count + source.like_count + source.laugh_count + "");
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


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EsTmp4 implements Serializable {
        EsTmp4.PostRelate _source;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class PostRelate {
            @JSONField(name = "url")
            @Flag
            private String post_url;
            @JSONField(name = "id")
            @Flag
            private String comment_id;
            @JSONField(name = "user_id")
            @Flag
            private String reviewer;
            @JSONField(name = "reviewer_url")
            @Flag
            private String reviewer_url;
            @JSONField(name = "content")
            @Flag
            private String comment;
            @JSONField(name = "created_at_date")
            @Flag
            private String date;
            @JSONField(name = "mood_cnt")
            @Flag
            private String mood_cnt;


            private int laugh_count;
            private int angry_count;
            private int like_count;
            private int sad_count;

            EsTmp4.RePostTmp repost_status;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class RePostTmp {
            @JSONField(name = "url")
            @Flag
            private String post_url;
        }

    }




    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EsTmpFriend implements Serializable {


        FriendsTmp _source;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class FriendsTmp {
            String friend_id;
            String user_id;
        }
    }
}
