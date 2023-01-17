package com.zero.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.utils.AbstractParameterTool;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 主函数模板
 *
 * @author zhangxuecheng4441
 * @date 2022/10/31/031 19:36
 */
@Slf4j
public class FlinkStreamModel {
    public static StreamExecutionEnvironment env;
    public static final String DEV_ENV = "dev";
    public static final String HK_ENV = "hk";
    public static final String ENV_PARAM = "env";

    /**
     * @param params params
     * @return StreamExecutionEnvironment
     * @throws IOException IOException
     */
    public static StreamExecutionEnvironment streamEnv(AbstractParameterTool params) throws IOException {

        long checkpointInterval = params.getLong("checkpointInterval", 60000);
        int parallelism = params.getInt("parallelism", 2);
        // set up the execution environment
        env = StreamEnvBuilder.builder()
                .setCheckpointInterval(checkpointInterval)
                .setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
                .setCheckpointTimeout(60000L)
                .setMinPauseBetweenCheckpoints(5000)
                .setTolerableCheckpointFailureNumber(3)
                .setMaxConcurrentCheckpoints(1)
                .setDefaultRestartStrategy(
                        5, Time.of(5, TimeUnit.MINUTES), Time.of(2, TimeUnit.MINUTES))
                .setParallelism(parallelism)
                .build();

        //根据环境 加载配置文件
        String envConf = params.get(ENV_PARAM, HK_ENV);

        InputStream resourceAsStream = FlinkStreamModel.class.getResourceAsStream("/" + String.format("application-%s.properties", envConf));
        ParameterTool parameterTool = ParameterTool.fromPropertiesFile(resourceAsStream);

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(parameterTool);
        return env;
    }


}
