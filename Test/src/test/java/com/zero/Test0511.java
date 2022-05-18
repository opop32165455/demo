package com.zero;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.zero.Flag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.var;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangxuecheng4441
 * @date 2022/5/11/011 10:49
 */
public class Test0511 {
    /**
     * tw 用户
     */
    @Test
    public void testUser() {
        var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0511\\user.csv", Charset.defaultCharset());
        String hbaseJson = FileUtil.readString("0511/hbase.json", Charset.defaultCharset());

        List<UserTmp> userTmpList = JSON.parseArray(hbaseJson, UserTmp.class);

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

    @Test
    public void testFriend() {
        try (CsvWriter writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0510\\friends.csv", Charset.defaultCharset())) {
            String esJson = FileUtil.readString("0510/friends.json", Charset.defaultCharset());
            List<EsTmp> esTmpList = JSON.parseArray(esJson, EsTmp.class);

            writer.write(new String[]{"user_id", "friend_id"});
            //esTmpList.stream()
            //        .map(EsTmp::get_source)
            //        .forEach(source -> writer.write(new String[]{source.user_id, source.friend_id}));
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTwLike() {
        try (CsvWriter writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0511\\tw_like.csv", Charset.defaultCharset())) {
            String esJson = FileUtil.readString("0511/tw_like.json", Charset.defaultCharset());
            List<EsLikeTmp> esTmpList = JSON.parseArray(esJson, EsLikeTmp.class);

            writer.write(new String[]{"post_id", "user_id", "capture_user_id"});
            esTmpList.stream()
                    .map(EsLikeTmp::get_source)
                    .forEach(source -> writer.write(new String[]{source.post_id, source.user_id, source.capture_user_id}));
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EsLikeTmp implements Serializable {
        LikeTmp _source;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class LikeTmp {
            @Flag
            @JSONField(name = "post_id")
            private String post_id;
            @Flag
            @JSONField(name = "like_id")
            private String user_id;
            @Flag
            @JSONField(name = "user_id")
            private String capture_user_id;
        }

    }


    @Test
    public void testPost() {
        try (var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0511\\tw_post.csv", Charset.defaultCharset());) {
            String esJson = FileUtil.readString("0511/post.json", Charset.defaultCharset());
            List<EsTmp> esTmpList = JSON.parseArray(esJson, EsTmp.class);


            ArrayList<String> fieldName = new ArrayList<>();
            for (Field declaredField : EsTmp.PostTmp.class.getDeclaredFields()) {
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
                        source.setPhotos(Optional.of(source)
                                .map(EsTmp.PostTmp::getExtended_entities)
                                .map(EsTmp.PostTmp.Entity::getMedia)
                                .map(mediaList -> {
                                    List<String> collect = mediaList.stream().map(EsTmp.PostTmp.Entity.Media::getMedia_url).collect(Collectors.toList());
                                    return CollUtil.join(collect, ",");
                                })
                                .orElse("")
                        );
                        source.setQuote_url(Optional.ofNullable(source.getRepost_status()).map(EsTmp.PostTmp.RePostTmp::getUrl).orElse(""));
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
    public void testRePost() {
        try (var writer = CsvUtil.getWriter("F:\\mySpace\\my_code\\CirclePenguin\\Test\\src\\test\\resources\\0511\\tw_repost.csv", Charset.defaultCharset());) {
            String esJson = FileUtil.readString("0511/repost.json", Charset.defaultCharset());
            List<EsTmp2> esTmpList = JSON.parseArray(esJson, EsTmp2.class);


            ArrayList<String> fieldName = new ArrayList<>();
            for (Field declaredField : EsTmp2.RepostTmp.class.getDeclaredFields()) {
                declaredField.setAccessible(true);
                if (declaredField.getAnnotation(Flag.class) != null) {
                    fieldName.add(declaredField.getName());
                }
            }
            String[] name = ArrayUtil.toArray(fieldName, String.class);
            writer.write(name);

            esTmpList.stream()
                    .map(EsTmp2::get_source)
                    .peek(source -> {
                        source.setPost_id(Optional.ofNullable(source.getRepost_status()).map(EsTmp2.RepostTmp.RePostTmp::getId).orElse(""));
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
        static class PostTmp {
            @Flag
            @JSONField(name = "id")
            private String id;
            @Flag
            @JSONField(name = "url")
            private String link;
            @Flag
            @JSONField(name = "created_at")
            private String date;
            @Flag
            @JSONField(name = "user_id")
            private String user_id;
            @Flag
            @JSONField(name = "nickname")
            private String username;
            @Flag
            @JSONField(name = "place")
            private String place;
            @Flag
            @JSONField(name = "place_cn")
            private String place_cn;
            @Flag
            @JSONField(name = "place_id")
            private String place_id;
            @Flag
            @JSONField(name = "place_code")
            private String place_code;
            @Flag
            @JSONField(name = "geo")
            private String geo;
            @Flag
            @JSONField(name = "terminal")
            private String source;
            @Flag
            @JSONField(name = "hash_tags")
            private String hashtags;
            @Flag
            @JSONField(name = "cashtags")
            private String cashtags;
            @Flag
            @JSONField(name = "lang")
            private String language;
            @Flag
            @JSONField(name = "content")
            private String tweet;
            @Flag
            @JSONField(name = "tweet_cn")
            private String tweet_cn;
            @Flag
            @JSONField(name = "mention_users")
            private String memtions;
            @Flag
            @JSONField(name = "reply_count")
            private String replies;
            @Flag
            @JSONField(name = "retweet_count")
            private String retweets;
            @Flag
            @JSONField(name = "like_count")
            private String likes;
            @Flag
            @JSONField(name = "quote_url")
            private String quote_url;
            @Flag
            @JSONField(name = "quoted")
            private String quoted;
            @Flag
            @JSONField(name = "refer_url")
            private String refer_url;
            @Flag
            @JSONField(name = "reply_url")
            private String reply_url;
            @Flag
            @JSONField(name = "photos")
            private String photos;

            RePostTmp repost_status;
            Entity extended_entities;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            static class RePostTmp {
                String url;
                Entity extended_entities;
            }

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            static class Entity {
                List<Media> media;

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                static class Media {
                    String media_url;
                }
            }

        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EsTmp2 implements Serializable {

        RepostTmp _source;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class RepostTmp {
            @Flag
            @JSONField(name = "post_id")
            private String post_id;
            @Flag
            @JSONField(name = "id")
            private String comment_id;
            @Flag
            @JSONField(name = "user_id")
            private String user_id;
            @Flag
            @JSONField(name = "content")
            private String comment;
            @Flag
            @JSONField(name = "comment_cn")
            private String comment_cn;
            @Flag
            @JSONField(name = "created_at")
            private String date;
            @Flag
            @JSONField(name = "terminal")
            private String source;
            @Flag
            @JSONField(name = "retweet_count")
            private String retweets;
            @Flag
            @JSONField(name = "like_count")
            private String favorite;
            @Flag
            @JSONField(name = "reply_count")
            private String replies;
            @Flag
            @JSONField(name = "mention_users")
            private String mentions;
            @Flag
            @JSONField(name = "in_reply_to_user_id")
            private String reply_to;
            @Flag
            @JSONField(name = "photos")
            private String photos;
            @Flag
            @JSONField(name = "screen_name")
            private String name;
            @Flag
            @JSONField(name = "nickname")
            private String username;
            @Flag
            @JSONField(name = "username_cn")
            private String username_cn;
            @Flag
            @JSONField(name = "bio")
            private String bio;
            @Flag
            @JSONField(name = "bio_cn")
            private String bio_cn;
            @Flag
            @JSONField(name = "location")
            private String location;
            @Flag
            @JSONField(name = "location_cn")
            private String location_cn;
            @Flag
            @JSONField(name = "url")
            private String url;
            @Flag
            @JSONField(name = "join_time")
            private String join_time;
            @Flag
            @JSONField(name = "tweets")
            private String tweets;
            @Flag
            @JSONField(name = "following")
            private String following;
            @Flag
            @JSONField(name = "followers")
            private String followers;
            @Flag
            @JSONField(name = "likes")
            private String likes;
            @Flag
            @JSONField(name = "media")
            private String media;
            @Flag
            @JSONField(name = "private")
            private String private_1;
            @Flag
            @JSONField(name = "verified")
            private String verified;
            @Flag
            @JSONField(name = "profile_image_url")
            private String profile_image_url;
            @Flag
            @JSONField(name = "backgroup_image")
            private String backgroup_image;

            RePostTmp repost_status;
            EsTmp.PostTmp.Entity extended_entities;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            static class RePostTmp {
                String url;
                String id;
                EsTmp.PostTmp.Entity extended_entities;
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
