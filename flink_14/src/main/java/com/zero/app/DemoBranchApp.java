package com.zero.app;

import com.zero.model.FlinkBranchModel;
import lombok.val;
import org.apache.flink.api.common.io.FileInputFormat;
import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.core.io.InputSplit;

import java.io.IOException;

/**
 * @author R4441-zxc
 * @create 2023/1/17 16:41
 */
public class DemoBranchApp extends FlinkBranchModel {
    public static void main(String[] args) throws Exception {
        val dataSource = env.createInput(new FileInputFormat<String>() {
            @Override
            public boolean reachedEnd() throws IOException {
                return false;
            }

            @Override
            public String nextRecord(String s) throws IOException {
                return "100";
            }
        });

        dataSource.print();
        env.execute("demo branch exec");
    }
}
