package com.fromZero.zeroSchema.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.fromZero.zeroSchema.Utils.JsonValidateUtil;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/13/013 16:03
 */
@RestController
@RequestMapping("/json")
public class JsonSchemaController extends ApiController {

    //测试使用json/testJson.txt中的json
    @PostMapping("/verifyJson")
    public R verifyJsonByJsonSchema(@RequestBody String info) {

        JSONObject jsonObject = JSONUtil.parseObj(info);
        String json = jsonObject.getStr("json");
        String jsonSchema = jsonObject.getStr("jsonSchema");

        try {
            ProcessingReport processingReport  = JsonValidateUtil.validatorJsonSchema(jsonSchema, json);
            boolean success = processingReport.isSuccess();
            return success(success);
        } catch (IOException e) {
            logger.error("解析过程出现错误");
            e.printStackTrace();
            return failed("解析过程出现错误");
        }

    }
}
