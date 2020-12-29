package com.fromZero.zero;


import com.fromZero.zeroSchema.JsonSchemaApplication;
import com.fromZero.zeroSchema.Utils.ReadJsonFile;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.fromZero.zeroSchema.Utils.JsonValidateUtil.validatorJsonSchema;

@SpringBootTest(classes = JsonSchemaApplication.class)
@RunWith(SpringRunner.class)
public class JsonSchemaApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("1");
    }

    @Test
    public void testschema() throws Exception {
        String data = ReadJsonFile.readJsonFileAsString("/json/testString.json");
        String schema = ReadJsonFile.readJsonFileAsString("/json/testSchema.json");
        ProcessingReport processingReport = validatorJsonSchema(schema, data);
        boolean success = processingReport.isSuccess();
        System.out.println(success);
//        如下方法可以用来接口自动化
//        Assert.assertTrue(report.isSuccess());
    }

}
