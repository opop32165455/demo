package com.zero.model;

import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.CollectionEnvironment;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.AbstractParameterTool;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author R4441-zxc
 * @create 2023/1/17 16:31
 */
public class FlinkBranchModel {
    public static ExecutionEnvironment env;

    /**
     * 创建branch env
     *
     * @param params params
     * @return ExecutionEnvironment
     * @throws IOException IOException
     */
    public static ExecutionEnvironment branchEnv(AbstractParameterTool params) throws IOException {

        long checkpointInterval = params.getLong("checkpointInterval", 60000);
        int parallelism = params.getInt("parallelism", 2);
        env = ExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(parallelism);

        //根据环境 加载配置文件
        String envConf = params.get("env", "dev");

        InputStream resourceAsStream = FlinkStreamModel.class.getResourceAsStream("/" + String.format("application-%s.properties", envConf));
        ParameterTool parameterTool = ParameterTool.fromPropertiesFile(resourceAsStream);

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(parameterTool);
        return env;
    }

}
