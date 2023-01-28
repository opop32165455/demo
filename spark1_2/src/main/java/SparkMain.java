import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.deploy.SparkHadoopUtil;

import java.util.Arrays;

/**
 * @author zhangxuecheng4441
 * @date 2022/9/9/009 10:41
 */
public class SparkMain {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Spark Java");

        JavaSparkContext sc = new JavaSparkContext(conf);

        try {
            JavaRDD<String> rddStr = sc.textFile("hdfs://hdp1:8020/app-logs/root/audit.log");

            JavaRDD<String> listStrRdd = rddStr.flatMap(new FlatMapFunction<String, String>() {
                private static final long serialVersionUID = 1L;
                @Override
                public Iterable<String> call(String s) throws Exception {
                    return Arrays.asList(s.split(" "));
                }
            });

            listStrRdd.foreach(new VoidFunction<String>() {
                private static final long serialVersionUID = 1L;
                @Override
                public void call(String x) throws Exception {
                    System.out.println(x);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        sc.close();
    }
}
