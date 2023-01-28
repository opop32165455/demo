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
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
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
import com.zero.dto.DemoData;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.InputStream;
import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestApplicationTests {


    @Test
    public void testMap() {
        HashMap<String, String> map = MapUtil.newHashMap();
        map.put("1", "2");

        String s = map.get("version");
        System.out.println("s = " + s);


    }

    @Test
    public void integer_test() {

        var writer = CsvUtil.getWriter("D:\\system-info\\桌面\\nebula调研\\test.csv", CharsetUtil.CHARSET_UTF_8);

        var names = CollUtil.newArrayList("amini_85",
                "EricVolat",
                "AlertyQ",
                "mcktestssss",
                "馃嚠馃嚪Mazandaran of Iran馃嚠馃嚪郾",
                "鄄郯",
                "丕賵賳噩丕 馃憠",
                "yamthrsfavnigga",
                "kaylaxsamantha",
                "Mersad__75",
                "VjcImtqoQfS3lcd",
                "Yahaidar265",
                "khamenei_ir",
                "JohnLilburne4",
                "jsnider31",
                "amini_85",
                "EricVolat",
                "AlertyQ",
                "mcktestssss",
                "馃嚠馃嚪Mazandaran of Iran馃嚠馃嚪郾:鄄郯",
                "丕賵賳噩丕 馃憠");

        var events = CollUtil.newArrayList(
                "event_1003",
                "event_1004",
                "event_1005",
                "event_1006"
        );

        var writeData = IntStream.range(1, 10 * 10000)
                .mapToObj(i -> new String[]{
                        "Involve_0" + i,
                        "Twitter" + RandomUtil.randomInt(0, 100 * 10000),
                        events.get(RandomUtil.randomInt(0, 3))
                })
                .collect(Collectors.toList());


        writer.write(writeData);
    }

    @Test
    public void read() {
        var reader = CsvUtil.getReader();
        var data = reader.read(new File("D:\\system-info\\桌面\\nebula调研\\线上数据\\事件1相关的KOL账号.csv"), CharsetUtil.CHARSET_UTF_8);
        for (CsvRow datum : data) {
            System.out.println("datum.toString() = " + datum.toString());
        }

        var writer = CsvUtil.getWriter("D:\\system-info\\桌面\\nebula调研\\线上数据\\账号.csv", CharsetUtil.CHARSET_UTF_8);
        writer.write();
    }

    @Test
    public void readTxt() {
        var reader = CsvUtil.getReader();
        var data = reader.read(new File("D:\\system-info\\桌面\\data\\dmp\\tw_id.csv"), CharsetUtil.CHARSET_UTF_8);

        HashSet<String> dataAcc = CollUtil.newHashSet();

        int r = 0;
        for (CsvRow row : data.getRows()) {
            dataAcc.add(row.get(0));
            System.out.println("read:" + r++);
        }

        var writer = CsvUtil.getWriter("D:\\system-info\\桌面\\data\\dmp\\tw_id_add_prefix.csv", CharsetUtil.CHARSET_UTF_8);
        int w = 0;
        for (String one : Collections.unmodifiableSet(dataAcc)) {
            writer.write(new String[]{"fb#RHINO#" + one});
            System.out.println("write = " + w++);
        }

    }

    @Test
    public void splitTxt() {
        var reader = CsvUtil.getReader();
        var data = reader.read(new File("D:\\system-info\\桌面\\data\\dmp\\tw_id_add_prefix.csv"), CharsetUtil.CHARSET_UTF_8);

        final byte[] count = {1};
        List<CsvRow> rows = data.getRows();
        ListUtil.partition(rows, 10 * 10000).forEach(i -> {

            var writer = CsvUtil.getWriter("D:\\system-info\\桌面\\data\\dmp\\tw_id_add_prefix-" + count[0] + ".csv", CharsetUtil.CHARSET_UTF_8);

            AtomicInteger w = new AtomicInteger();
            i.forEach(one -> {
                writer.write(new String[]{one.get(0)});
                System.out.println("write = " + w.getAndIncrement());
            });

            count[0] = (byte) (count[0] + 1);
        });

    }

    @Builder
    static class SysUser {
        Integer id;
        String email;
    }

    @Test
    public void demo1() {
        System.out.println("log = " + log);
        ExcelReader reader = ExcelUtil.getReader("D:\\system-info\\桌面\\data\\dmp\\240w.xlsx", 0);
        //var mapList = reader.read(0, 0, 50 * 1000);

        CsvWriter writer = CsvUtil.getWriter(new File("D:\\system-info\\桌面\\data\\dmp\\test-1.csv"), Charset.defaultCharset());

        IntStream.rangeClosed(1, 1001).forEach(i -> {
            System.out.println("i = " + i);
            var mapList = reader.read(0, (i - 1) * 1000, i * 1000);
            List<String[]> collect = mapList.stream().map(s -> new String[]{s.getOrDefault("user_id", "unkown").toString(), s.getOrDefault("nickname", "unkown").toString()}).collect(Collectors.toList());
            writer.write(collect);
        });

        ReadWorkbook readWorkbook = new ReadWorkbook();
        File file = new File("D:\\system-info\\桌面\\data\\dmp\\240w.xlsx");
        readWorkbook.setFile(file);

    }

    /**
     * 最简单的读
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>3. 直接读即可
     */
    @Test
    public void simpleRead() {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭

        var fileName = "D:\\system-info\\桌面\\data\\dmp\\240w.xlsx";

        CsvWriter writer = CsvUtil.getWriter(new File("D:\\system-info\\桌面\\data\\dmp\\test-1.csv"), Charset.defaultCharset());

        final Integer[] i = {0};
        EasyExcel.read(fileName, DemoData.class, new PageReadListener<DemoData>(dataList -> {

            i[0] = i[0] + dataList.size();
            System.out.println("i = " + i[0]);

            Set<String[]> collect = dataList.stream()
                    .map(s -> new String[]{s.getId(), "taiwan", s.getName1(), s.getName2(), s.getUrl()})
                    .collect(Collectors.toSet());

            writer.write(collect);
        })).sheet("Sheet2").doRead();
    }

    @Test
    public void simpleReadId() {
        var fileName1 = "D:\\system-info\\桌面\\data\\dmp\\700w_friend_id_hash4.csv";
        var fileName2 = "D:\\system-info\\桌面\\data\\dmp\\account_240w_tw-2.csv";
        var fileName3 = "D:\\system-info\\桌面\\data\\dmp\\account_240w_tw-3.csv";
        var fileName = "D:\\system-info\\桌面\\data\\dmp\\id-2.csv";
        var data1 = CsvUtil.getReader().read(new File(fileName1), CharsetUtil.CHARSET_UTF_8);
      /*  var data2 = CsvUtil.getReader().read(new File(fileName2), CharsetUtil.CHARSET_UTF_8);
        var data3 = CsvUtil.getReader().read(new File(fileName3), CharsetUtil.CHARSET_UTF_8);*/


        var writer = CsvUtil.getWriter("D:\\system-info\\桌面\\data\\dmp\\700w_hash4_friend_handle.csv", CharsetUtil.CHARSET_UTF_8);
        AtomicInteger w = new AtomicInteger();


        data1.getRows()
                .stream()
                .map(s -> new String[]{"friend_" + s.get(0) + "_" + s.get(1), s.get(0), s.get(1)})
                .peek(s -> {
                    int andIncrement = w.getAndIncrement();
                    System.out.println("andIncrement = " + andIncrement);
                })
                .forEach(writer::write);


    }

    @Test
    public void tgId() {
        String readString = FileUtil.readString(new File("D:\\system-info\\下载\\2019-10w-user.txt"), Charset.defaultCharset());

    /*    String ssss = readString.replaceAll("\r", "");
        ssss = ssss.replaceAll("\n", "");*/
        String body = com.alibaba.fastjson.JSONObject.toJSONString(readString);
        Object parse1 = JSON.parse(body);
        String ps = parse1.toString();
        List<TgUser> tgUsers = JSON.parseArray(ps, TgUser.class);

        // JSONArray jsonArray = JSONUtil.parseArray(readString);


        var writer = CsvUtil.getWriter("D:\\system-info\\桌面\\data\\tg\\2019-10w-user.csv", CharsetUtil.CHARSET_UTF_8);
        AtomicInteger w = new AtomicInteger();


    }

    @Test
    public void newTg() {
        // String readString = FileUtil.readString(new File("D:\\system-info\\下载\\2019-5w-user.txt"), Charset.defaultCharset());

        ArrayList<String> fileName = new ArrayList<>();

        while (true) {
            List<File> files = FileUtil.loopFiles("D:\\system-info\\桌面\\data\\tg\\txt");
            for (File file : files) {
                String name = file.getName();
                if (!fileName.contains(name)) {

                    parseTxt(name);

                    fileName.add(name);

                }
                ThreadUtil.sleep(1000);
            }
        }
    }

    @Test
    public void newTw100w() {
        // String readString = FileUtil.readString(new File("D:\\system-info\\下载\\2019-5w-user.txt"), Charset.defaultCharset());

        Set<String> twitterIds = new HashSet<>();

        ExcelWriter excelWriter = ExcelUtil.getWriter("D:\\system-info\\桌面\\data\\0306tw\\" + "0306-1.2k-tw-hk-id" + ".xlsx");
        //List<File> files = FileUtil.loopFiles("D:\\system-info\\桌面\\data\\0306tw");
        //for (File file : files) {

        List<String> strings = FileUtil.readLines(new File("D:\\system-info\\桌面\\data\\tg\\tg0307\\2019-2020-group (2).txt"), Charset.defaultCharset());

        for (String string : strings) {
            if (string.contains("key")) {

                String str = StrUtil.removeAll(string.trim(), "\"key\": \"");
                String str2 = StrUtil.removeAll(str.trim(), "\",");
                twitterIds.add(str2);
            }
        }

        // }
        for (String twitterId : twitterIds) {
            excelWriter.writeRow(CollUtil.newArrayList(twitterId, true));
        }

        excelWriter.close();

    }

    @Test
    public void newLianDeng() {
        List<String> strings = FileUtil.readLines(new File("D:\\system-info\\桌面\\data\\lian-deng\\" + "lian-deng-2022-2k.txt"), Charset.defaultCharset());

        //var writer = CsvUtil.getWriter("D:\\system-info\\桌面\\data\\tg\\user-400-info.csv", Charset.defaultCharset());

        ExcelWriter excelWriter = ExcelUtil.getWriter("D:\\system-info\\桌面\\data\\lian-deng\\" + "lian-deng-2022-2k" + ".xlsx");

        int i = 0;
        TgUser tgUser = new TgUser();
        List<TgUser> tList = new ArrayList<>();

        for (String string : strings) {
            System.out.println("line = " + i++);
            if (string.contains("key")) {
                if (tgUser.getKey() != null) {
                    // writer.write(new String[]{tgUser.getKey(), tgUser.getGroup_id(), tgUser.nickname, tgUser.screen_name});
                    // excelWriter.writeRow(CollUtil.newArrayList(tgUser.getKey(), tgUser.getGroup_id(), tgUser.nickname, tgUser.screen_name), true);
                    tList.add(tgUser);
                }
                tgUser = new TgUser();
                String str = StrUtil.removeAll(string.trim(), "\"key\": \"");
                String str2 = StrUtil.removeAll(str.trim(), "\",");
                tgUser.setKey(str2);
            } else if (string.contains("doc_count")) {
                String str = StrUtil.removeAll(string.trim(), "doc_count");
                String str2 = StrUtil.removeAll(str.trim(), ":");
                String str3 = StrUtil.removeAll(str2.trim(), "\"").trim();
                tgUser.setDoc_count(StrUtil.isBlank(str3) ? 0L : Long.parseLong(str3));
            } else if (string.contains("screen_name")) {
                String str = StrUtil.removeAll(string.trim(), "\"screen_name\": \"");
                String str2 = StrUtil.removeAll(str.trim(), "\",");
                tgUser.setScreen_name(StrUtil.removeAll(str2.trim(), '"'));
            } else if (string.contains("group_id")) {
                String str = StrUtil.removeAll(string.trim(), "\"group_id\": \"");
                String str2 = StrUtil.removeAll(str.trim(), "\",");
                tgUser.setGroup_id(str2);
            } else if (string.contains("nickname")) {
                String str = StrUtil.removeAll(string.trim(), "\"nickname\": \"");
                String str2 = StrUtil.removeAll(str.trim(), "\",");
                tgUser.setNickname(StrUtil.removeAll(str2.trim(), '"'));
            } else {

            }

        }
        if (tgUser.getKey() != null) {
            //writer.write(new String[]{tgUser.getKey(), tgUser.getGroup_id(), tgUser.nickname, tgUser.screen_name});
            // excelWriter.writeRow(CollUtil.newArrayList(tgUser.getKey(), tgUser.getGroup_id(), tgUser.nickname, tgUser.screen_name), true);
            tList.add(tgUser);
        }

        Map<String, Long> idCount = tList.stream().collect(Collectors.toMap(TgUser::getKey, TgUser::getDoc_count));

        for (TgUser user : tList) {
            Long count = user.getDoc_count();
            excelWriter.writeRow(CollUtil.newArrayList(user.key, count == null ? 0L : count), true);
        }


        //  writer.close();
        excelWriter.close();
    }


    @Test
    public void parseDateData() {
        var String = FileUtil.readString(new File("D:\\system-info\\桌面\\data\\tg\\tg0307\\Result-2019-2022-group_mouth_count.txt"), Charset.defaultCharset());

        ArrayList<MouthAcc> mouths = CollUtil.newArrayList();

        String body = com.alibaba.fastjson.JSONObject.toJSONString(String);
        Object parse1 = JSON.parse(body);
        String ps = parse1.toString();
        JSONArray jsonArray = JSON.parseArray(ps);

        for (Object obj : jsonArray) {
            var accBuilder = MouthAcc.builder();
            var jsonObject = JSON.parseObject(obj.toString());
            accBuilder.id(jsonObject.getString("key"));
            accBuilder.allCount(jsonObject.getLong("doc_count"));

            var mountArr = jsonObject.getJSONObject("NAME").getJSONArray("buckets");

            LinkedHashMap<String, Long> mouthObjMap = new LinkedHashMap<>();

            //IntStream.rangeClosed(1, 31).forEach(i -> {
            //    String str = "2022-01-";
            //    if (i < 10) {
            //        str = str + "0" + i;
            //    } else {
            //        str = str + i;
            //    }
            //    if (!mouthObjMap.containsKey(str)) {
            //        mouthObjMap.put(str, 0L);
            //    }
            //});
            //IntStream.rangeClosed(1, 28).forEach(i -> {
            //    String str = "2022-02-";
            //    if (i < 10) {
            //        str = str + "0" + i;
            //    } else {
            //        str = str + i;
            //    }
            //    if (!mouthObjMap.containsKey(str)) {
            //        mouthObjMap.put(str, 0L);
            //    }
            //});
            //IntStream.rangeClosed(1, 7).forEach(i -> {
            //    String str = "2022-03-";
            //    if (i < 10) {
            //        str = str + "0" + i;
            //    } else {
            //        str = str + i;
            //    }
            //    if (!mouthObjMap.containsKey(str)) {
            //        mouthObjMap.put(str, 0L);
            //    }
            //});
            IntStream.rangeClosed(3, 12).forEach(i -> {
                String str = "2019-";
                if (i < 10) {
                    str = str + "0" + i;
                } else {
                    str = str + i;
                }
                if (!mouthObjMap.containsKey(str)) {
                    mouthObjMap.put(str, 0L);
                }
            });
            IntStream.rangeClosed(1, 12).forEach(i -> {
                String str = "2020-";
                if (i < 10) {
                    str = str + "0" + i;
                } else {
                    str = str + i;
                }
                if (!mouthObjMap.containsKey(str)) {
                    mouthObjMap.put(str, 0L);
                }
            });
            IntStream.rangeClosed(1, 12).forEach(i -> {
                String str = "2021-";
                if (i < 10) {
                    str = str + "0" + i;
                } else {
                    str = str + i;
                }
                if (!mouthObjMap.containsKey(str)) {
                    mouthObjMap.put(str, 0L);
                }
            });
            IntStream.rangeClosed(1, 3).forEach(i -> {
                String str = "2022-";
                if (i < 10) {
                    str = str + "0" + i;
                } else {
                    str = str + i;
                }
                if (!mouthObjMap.containsKey(str)) {
                    mouthObjMap.put(str, 0L);
                }
            });

            for (Object mouthObj : mountArr) {
                JSONObject mouthJson = JSON.parseObject(mouthObj.toString());

                String str = mouthJson.getString("key_as_string");
                /*String pattern = "(?!\\\\2022)([0-9]{2})[-][0-9]{2}(?!\\\\T00:00:00.000+08:00)";

                Pattern r = Pattern.compile(pattern);
                Matcher matcher = r.matcher(str);
                if (matcher.find()) {
                    var dateName = matcher.group(0);
                    mouthObjMap.put(dateName, mouthJson.getLong("doc_count"));
                }*/
                String result = StrUtil.removeSuffix(str, "-01T00:00:00.000+08:00");
                mouthObjMap.put(result, mouthJson.getLong("doc_count"));

            }

            var mouthAcc = accBuilder.mouthCount(mouthObjMap).build();
            mouths.add(mouthAcc);

        }
        //  System.out.println("mouths = " + mouths);
        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList();
        ExcelWriter excelWriter = ExcelUtil.getWriter("D:\\system-info\\桌面\\data\\tg\\tg0307\\MONTH-2019-2022-group.xlsx");
        for (MouthAcc mouth : mouths) {
            LinkedHashMap<java.lang.String, Object> map = new LinkedHashMap<>(20);
            map.put("id", mouth.id);
            map.putAll(mouth.mouthCount);
            rows.add(map);
        }

        excelWriter.write(rows, true);
        excelWriter.close();
    }

    @lombok.Data
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class MouthAcc {
        String id;
        Long allCount;
        Map<String, Long> mouthCount;
    }

    @Test
    public void testNewTg() {
        parseTxt("Result-2022-user-group.txt");
    }

    private void parseTxt(String name) {
        List<String> strings = FileUtil.readLines(new File("D:\\system-info\\桌面\\data\\tg\\tg0307\\" + name), Charset.defaultCharset());
        // List<String> ids = FileUtil.readLines(new File("D:\\system-info\\桌面\\data\\tg\\group_id_mix.txt"), Charset.defaultCharset());

        //var writer = CsvUtil.getWriter("D:\\system-info\\桌面\\data\\tg\\user-400-info.csv", Charset.defaultCharset());

        ExcelWriter excelWriter = ExcelUtil.getWriter("D:\\system-info\\桌面\\data\\tg\\tg0307\\" + name + ".xlsx");

        int i = 0;
        TgUser tgUser = new TgUser();
        List<TgUser> tList = new ArrayList<>();

        for (String string : strings) {
            System.out.println("line = " + i++);
            if (string.contains("key")) {
                if (tgUser.getKey() != null) {
                    // writer.write(new String[]{tgUser.getKey(), tgUser.getGroup_id(), tgUser.nickname, tgUser.screen_name});
                    // excelWriter.writeRow(CollUtil.newArrayList(tgUser.getKey(), tgUser.getGroup_id(), tgUser.nickname, tgUser.screen_name), true);
                    tList.add(tgUser);
                }
                tgUser = new TgUser();
                String str = StrUtil.removeAll(string.trim(), "\"key\": \"");
                String str2 = StrUtil.removeAll(str.trim(), "\",");
                tgUser.setKey(str2);
            } else if (string.contains("doc_count")) {
                String str = StrUtil.removeAll(string.trim(), "doc_count");
                String str2 = StrUtil.removeAll(str.trim(), ":");
                String str3 = StrUtil.removeAll(str2.trim(), "\"").trim();
                tgUser.setDoc_count(StrUtil.isBlank(str3) ? 0L : Long.parseLong(str3));
            } else if (string.contains("screen_name")) {
                String str = StrUtil.removeAll(string.trim(), "\"screen_name\": \"");
                String str2 = StrUtil.removeAll(str.trim(), "\",");
                tgUser.setScreen_name(StrUtil.removeAll(str2.trim(), '"'));
            } else if (string.contains("group_id")) {
                String str = StrUtil.removeAll(string.trim(), "\"group_id\": \"");
                String str2 = StrUtil.removeAll(str.trim(), "\",");
                tgUser.setGroup_id(str2);
            } else if (string.contains("nickname")) {
                String str = StrUtil.removeAll(string.trim(), "\"nickname\": \"");
                String str2 = StrUtil.removeAll(str.trim(), "\",");
                tgUser.setNickname(StrUtil.removeAll(str2.trim(), '"'));
            } else {

            }

        }
        if (tgUser.getKey() != null) {
            //writer.write(new String[]{tgUser.getKey(), tgUser.getGroup_id(), tgUser.nickname, tgUser.screen_name});
            // excelWriter.writeRow(CollUtil.newArrayList(tgUser.getKey(), tgUser.getGroup_id(), tgUser.nickname, tgUser.screen_name), true);
            tList.add(tgUser);
        }

        Map<String, Long> idCount = tList.stream().collect(Collectors.toMap(TgUser::getKey, TgUser::getDoc_count));

        for (TgUser user : tList) {
            excelWriter.writeRow(CollUtil.newArrayList(user.key, user.getDoc_count() == null ? 0L : user.doc_count), true);
        }

      /*  for (String id : ids) {
            Long count = idCount.get(id);
            excelWriter.writeRow(CollUtil.newArrayList(id, count == null ? 0L : count), true);
        }
*/
        //  writer.close();
        excelWriter.close();
    }


    public static void main1(String[] args) {

        List<String> strings = FileUtil.readLines(new File("D:\\system-info\\桌面\\data\\tg\\group_id_mix.txt"), Charset.defaultCharset());

        List<String> collect = strings.stream()
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.toList());
        String join = CollUtil.join(collect, ",");
        System.out.println("join = " + join);
        /*    *//*    String ssss = readString.replaceAll("\r", "");
        ssss = ssss.replaceAll("\n", "");*//*
        String body = com.alibaba.fastjson.JSONObject.toJSONString(readString);
        Object parse1 = JSON.parse(body);
        String ps = parse1.toString();
        List<TgUser> tgUsers = JSON.parseArray(ps, TgUser.class);

        // JSONArray jsonArray = JSONUtil.parseArray(readString);


        AtomicInteger w = new AtomicInteger();*/

    }

    @Test
    public void id2Qb() {
        List<String> strings = FileUtil.readLines(new File("D:\\system-info\\桌面\\data\\tg\\tg0307\\ID.txt"), Charset.defaultCharset());

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        ListUtil.partition(strings, 1000).forEach(i -> {
            boolQuery.should(QueryBuilders.termsQuery("group_id", i));
        });

        System.out.println("queryBuilder = " + boolQuery);


    }

    @Test
    public void kw2Qb() {
        String d2019 = "林奠|柒婆|777|689|總加速師|一黨專政|五毛|支那|支共|光復|抗爭|國殤|奶共|共匪|習特勒|政治犯|中共膠|藍屍|中華膠|賤民|韭菜|獨裁|蟻民|極權|威權|暴政|傀儡|習帝|阿爺|滅共|港共|一國一制|愛黨|獅子山|傀儡|牆國|戰狼|淋奠|牆內|狗共|港殤|革命|狗官|投共|左膠|共諜|特衰政府|粉蛆|亡共|滅共|集中營|種族滅絕|鎮壓|打壓|平反六四|天安門屠殺|監逝|良知|黨鐵|勿忘六四|8964|港豬|躺平|清一色|小圈子|垃圾會|保皇黨|忠誠廢物|分餅仔|政治花瓶|民賤聯|白票|廢票|唔投票|杯葛|抵制|不義選舉|真普選|假選舉|保皇狗|陪跑|做騷|橡皮圖章|操縱|操控|假民主|白色恐怖|宇宙法|紅線|郭安|党安法|國安大法|宇宙割安法|文字獄|惡法|蟈安|國際線|打壓|鎮壓|清算|肆意拘捕|黑警|義士|送頭|屠狗|斬警|梁健輝烈士|同路人|手足|警曱|be water|如水再聚|黑狗|走狗|攬炒|重光|皇軍|煲底見|政治犯|抗爭|對抗|齊上齊落|自決|濫捕|濫暴|武漢病毒|中共病毒|武漢肺炎|強封|強檢|監控|毒針|洪門宴|疫苗外交|蝙蝠女|焚報坑儒|藍媒|熱狗|CCTVB|文字獄|寒蟬效應|以言入罪|爆買|打壓|新聞自由|噤若寒蟬";
        String d2022 = "林奠|柒婆|777|689|總加速師|一黨專政|五毛|支那|支共|光復|抗爭|國殤|奶共|共匪|習特勒|政治犯|中共膠|藍屍|中華膠|賤民|韭菜|獨裁|蟻民|極權|威權|暴政|傀儡|習帝|阿爺|滅共|港共|一國一制|愛黨|獅子山|傀儡|牆國|戰狼|淋奠|牆內|狗共|港殤|革命|狗官|投共|左膠|共諜|特衰政府|粉蛆|亡共|滅共|集中營|種族滅絕|鎮壓|打壓|齊上齊落|自決|濫捕|濫暴|武漢病毒|中共病毒|武漢肺炎|強封|強檢|監控|毒針|洪門宴|疫苗外交|蝙蝠女|死城|迫針|谷針|武肺|joke糕灣|反苗|just a flu|反隔離|苗撚|細菌戰|集中營|隔離營|大爆發|隔離師|檢測煉獄|竹篙灣|坐監|暴力清零|回力鏢|送中|經濟清零|DNA|大龍鳯|垃圾政府|彈出彈入|竹9灣|兩頭唔到岸|共存|群體免疫|屎眼實驗室|防疫無用|動態training|#回力鏢|#攬炒|#支共攬炒香港|#暴力清零|#鐵腕清零|#武漢肺炎|#支那病毒|#支共禍患|#禍港正苦|#柒婆|#躺平|#暴力清零|#鐵腕清零|#不是天災是人禍|#手足同心|#打破宿命|#香港加油|#谷針|#苛政猛於疫情|#抗疫為名|#港共落地獄|#清零|#放風治港|#封城|#禁足|#盲搶潮|#武肺日常|#通你老母關|#清你老母零|勞民傷財|忠誠強檢|俄狗|俄妹|抗俄|仆街普京|基輔戰鬼|撐烏克蘭|俄豬|五盧布|5盧布|烏奸|普驚|獨裁|蛾狗|俄軍狗|蛾羅屍|俄仔|蛾仔|天佑烏克蘭|攻台|打台灣|爆台灣|武統台灣|#汽油彈|#火魔|#雞尾酒|援助烏克蘭|力挺烏克蘭|制裁俄羅斯|挺烏克蘭|共匪|烏克蘭捐款|#淪陷|#全面入侵|侵犯烏克蘭|入侵烏克蘭|鹅毛|俄匪|康米|核平|俄爹|俄的|俄孝子|五毛小粉蛆|黃俄|俄羅斯侵略者|祈禱烏克蘭|聲援烏克蘭|俄羅斯假消息|神佑烏克蘭|StandWithUkraine|烏克蘭加油|挺烏|意淫|烏克蘭美女|烏克蘭靚女|Anonymous|撐烏克蘭|集氣|抗議|願榮光歸烏克蘭|榮光|天佑烏克蘭|烏克蘭必勝|GloryToUkraine|普驚|獨裁|威權侵略|俄孝子蛆号们|黑警|普丁政權|俄恐怖分子|強盜邏輯|俄爹|支奴團隊|精英五毛|明日台灣|極權|排華|納粹|普京北极熊|日軍侵華|小粉紅|鹅爹|光復香港|光歸香港|時代革命|難民法|中共暴政|獨裁專制國家|#獨裁好朋友|港共|東方烏克蘭";
        HashSet<String> kw = new HashSet<>();
        for (String k1 : StrUtil.split(d2019, "|")) {
            kw.add(k1.trim());
        }

        for (String k1 : StrUtil.split(d2022, "|")) {
            kw.add(k1.trim());
        }
        String join = CollUtil.join(kw, "|");
        System.out.println("join = " + join);

    }

    @Test
    public void addId() {
        List<String> strings = FileUtil.readLines("D:\\system-info\\桌面\\data\\tg\\tg0307\\group_mix_2022+2019.txt", Charset.defaultCharset());

        List<String> collect = strings.stream().map(s -> "\"" + s + "\"").collect(Collectors.toList());
        String join = CollUtil.join(collect, ",");
        System.out.println("join = " + join);
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class TgUser {
        String key;
        Long doc_count;
        String screen_name;
        String nickname;
        String group_id;
    }

    @Test
    public void testHDFS(){


    }


}
