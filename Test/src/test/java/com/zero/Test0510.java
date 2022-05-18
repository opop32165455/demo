package com.zero;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.var;
import org.json.JSONObject;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangxuecheng4441
 * @date 2022/5/11/011 10:49
 */
public class Test0510 {
    @Test
    public void testUser() {
        var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0510\\user.csv", Charset.defaultCharset());
        String esJson = FileUtil.readString("0510/es.json", Charset.defaultCharset());
        String hbaseJson = FileUtil.readString("0510/hbase.json", Charset.defaultCharset());

        UserTmp userTmp = new Gson().fromJson(esJson, UserTmp.class);
        Map<String, Object> esMap = BeanUtil.beanToMap(userTmp);
        ArrayList<String> remoKeys = new ArrayList<>();
        esMap.forEach((k, v) -> {
            if (v == null) {
                remoKeys.add(k);
            }
        });
        remoKeys.forEach(esMap::remove);

        UserTmp userTmp1 = new Gson().fromJson(hbaseJson, UserTmp.class);
        Map<String, Object> hbaseMap = BeanUtil.beanToMap(userTmp1);
        ArrayList<String> remoKeys2 = new ArrayList<>();
        hbaseMap.forEach((k, v) -> {
            if (v == null) {
                remoKeys2.add(k);
            }
        });
        remoKeys2.forEach(hbaseMap::remove);

        esMap.putAll(hbaseMap);
        esMap.put("website", esMap.get("home_url"));
        esMap.put("banner", esMap.get("profile"));

        Field[] fields = UserTmp.class.getDeclaredFields();
        int length = fields.length;
        String[] names = new String[length];
        String[] data = new String[length];

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            String name = field.getName();
            names[i] = name;
            data[i] = esMap.getOrDefault(name, "").toString();
        }


        writer.write(names);
        writer.write(data);
        writer.close();
    }

    @Test
    public void testFriend() {


        try (CsvWriter writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0510\\friends.csv", Charset.defaultCharset())) {
            String esJson = FileUtil.readString("0510/friends.json", Charset.defaultCharset());
            List<EsTmp> esTmpList = JSON.parseArray(esJson, EsTmp.class);

            writer.write(new String[]{"user_id", "friend_id"});
            esTmpList.stream()
                    .map(EsTmp::get_source)
                    .forEach(source -> writer.write(new String[]{source.user_id, source.friend_id}));
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPost() {
        try (var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0510\\fb_post.csv", Charset.defaultCharset());) {
            String esJson = FileUtil.readString("0510/post.json", Charset.defaultCharset());
            List<EsTmp> esTmpList = JSON.parseArray(esJson, EsTmp.class);


            ArrayList<String> fieldName = new ArrayList<>();
            for (Field declaredField : PostTmp.class.getDeclaredFields()) {
                declaredField.setAccessible(true);
                if (declaredField.getAnnotation(Flag.class) != null) {
                    fieldName.add(declaredField.getName());
                }
            }
            String[] name = ArrayUtil.toArray(fieldName, String.class);
            writer.write(name);

            esTmpList.stream()
                    .map(EsTmp::get_source)
                    .peek(source -> {
                        source.setForward(Optional.ofNullable(source.getRepost_status()).map(PostTmp.RePostTmp::getUrl).orElse(""));
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

    @Test
    public void testMedia() {
        try (var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0510\\fb_media.csv", Charset.defaultCharset());) {
            String esJson = FileUtil.readString("0510/media.json", Charset.defaultCharset());
            List<EsTmp2> esTmpList = JSON.parseArray(esJson, EsTmp2.class);

            ArrayList<String> fieldName = new ArrayList<>();
            for (Field declaredField : MediaTmp.class.getDeclaredFields()) {
                declaredField.setAccessible(true);
                if (declaredField.getAnnotation(Flag.class) != null) {
                    fieldName.add(declaredField.getName());
                }
            }
            String[] name = ArrayUtil.toArray(fieldName, String.class);
            writer.write(name);

            esTmpList.stream()
                    .map(EsTmp2::get_source)
                    .map(ImageDescTmp::getImage_desc)
                    .flatMap(Collection::stream)
                    .map(BeanUtil::beanToMap)
                    .forEach(map -> {
                        ArrayList<String> data = new ArrayList<>();
                        for (String name1 : name) {
                            Object orDefault = map.getOrDefault(name1, "");
                            data.add(String.valueOf(orDefault));
                        }
                        writer.write(ArrayUtil.toArray(data, String.class));
                    });
        }
    }


    @Test
    public void testRePost() {
        try (var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0510\\post_related.csv", Charset.defaultCharset());) {
            String esJson = FileUtil.readString("0510/repost.json", Charset.defaultCharset());
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
                        source.setPost_url(Optional.ofNullable(source.getRepost_status()).map(EsTmp4.RePostTmp::getPost_url).orElse(""));
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
        PostRelate _source;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class PostRelate {
            @JSONField(name = "post_url")
            @Flag
            private String post_url;
            @JSONField(name = "user_id")
            @Flag
            private String user_id;
            @JSONField(name = "id")
            @Flag
            private String comment_id;
            @JSONField(name = "mood_url")
            @Flag
            private String mood_url;
            @JSONField(name = "share_url")
            @Flag
            private String share_url;

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
    static class EsTmp3 implements Serializable {


        PostTmp _source;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class PostTmp {
            @JSONField(name = "post_url")
            @Flag
            private String post_url;
            @JSONField(name = "id")
            @Flag
            private String comment_id;
            @JSONField(name = "user_id")
            @Flag
            private String reviewer;
            @JSONField(name = "url")
            @Flag
            private String reviewer_url;
            @JSONField(name = "content")
            @Flag
            private String comment;
            @JSONField(name = "created_at")
            @Flag
            private String date;
            @JSONField(name = "mood_cnt")
            @Flag
            private String mood_cnt;


            private String laugh_count;
            private String angry_count;
            private String like_count;
            private String sad_count;

            RePostTmp repost_status;
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
    static class ImageDescTmp implements Serializable {

        List<MediaTmp> image_desc;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class MediaTmp implements Serializable {
        @JSONField(name = "post_url")
        @Flag
        private String post_url;
        @JSONField(name = "media_type")
        @Flag
        private String media_type;
        @JSONField(name = "url")
        @Flag
        private String media_url;
        @JSONField(name = "fastdfs_path")
        @Flag
        private String media_path;
        @JSONField(name = "crawl_time")
        @Flag
        private String crawl_time;

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
        private RePostTmp repost_status;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class RePostTmp {
            String url;
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
    static class EsTmp2 implements Serializable {


        ImageDescTmp _source;

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
    static class UserTmp implements Serializable {
        @SerializedName("id")
        private String user_id;
        @SerializedName("screen_name")
        private String user_name;
        @SerializedName("nickname")
        private String nick_name;
        @SerializedName("url")
        private String home_url;
        @SerializedName("gender")
        private String sex;
        @SerializedName("contact")
        private String contact;
        @SerializedName("url2")
        private String website;
        @SerializedName("location")
        private String places_lived;
        @SerializedName("hometown")
        private String home_town;
        @SerializedName("work")
        private String work;
        @SerializedName("educations")
        private String education;
        @SerializedName("relationship_status")
        private String relationship;
        @SerializedName("family_members")
        private String family;
        @SerializedName("desc")
        private String signature;
        @SerializedName("quotes")
        private String quotes;
        @SerializedName("friends_count")
        private String friend_cnt;
        @SerializedName("like_count")
        private String like_cnt;
        @SerializedName("about_contents")
        private String about_contents;
        @SerializedName("profile_image_url")
        private String profile;
        @SerializedName("profile_image_url2")
        private String banner;
    }
}
