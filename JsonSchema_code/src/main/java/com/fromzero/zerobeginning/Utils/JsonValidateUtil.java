package com.fromzero.zerobeginning.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Iterator;

/**
 * @Desciption: 校验json数据是否符合schema约定的标准
 * level: 错误级别（应该就是error）
 * schema：引起故障的模式的所在位置的 URI
 * instance：错误对象
 * domain：验证域
 * keyword：引起错误的约束key
 * found：现在类型
 * expected：期望类型
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/13/013 15:40
 */
@Slf4j
public class JsonValidateUtil {
    private final static JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

    /**
     * 校验JSON
     * @param schema   json模式数据（可以理解为校验模板）
     * @param instance 需要验证Json数据
     * @return
     */
    public static ProcessingReport validatorJsonSchema(String schema, String instance) throws IOException {
        ProcessingReport processingReport = null;
        // JsonNode jsonSchema = JsonLoader.fromResource("/");
        // JsonNode jsonData = JsonLoader.fromResource("/");
        JsonNode jsonSchema = JsonLoader.fromString(schema);
        JsonNode jsonData = JsonLoader.fromString(instance);
        processingReport = factory.byDefault().getValidator().validateUnchecked(jsonSchema, jsonData);


        boolean success = processingReport.isSuccess();
        if (!success) {
            Iterator<ProcessingMessage> iterator = processingReport.iterator();
            while (iterator.hasNext()) {
                log.error(String.valueOf(iterator.next()));
            }
        }
        return processingReport;
    }
}