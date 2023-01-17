package com.zero.app;

import com.zero.model.FlinkStreamModel;
import lombok.val;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

/**
 * @author R4441-zxc
 * @create 2023/1/17 16:29
 */
public class DemoStreamApp extends FlinkStreamModel {

    public static void main(String[] args) {
        val streamSource = env.addSource(new RichSourceFunction<String>() {
            @Override
            public void run(SourceContext<String> sourceContext) throws Exception {
                int times = 1000;
                for (int i = 0; i < times; i++) {

                }
            }

            @Override
            public void cancel() {

            }
        });


    }
}
