import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.yaml.YamlUtil;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhangxuecheng4441
 * @date ${DATE} ${TIME}
 */
public class Main {
    public static void main(String[] args){
        try {
            System.out.println("args " + Arrays.toString(args));
            if (args != null) {

                for (String arg : args) {
                    if (StrUtil.startWith(arg, "-properties:")) {
                        String propStr = StrUtil.removePrefix(arg, "-properties:");
                        Properties pro = new Properties();
                        pro.load(new StringReader(propStr));
                        System.out.println("pro = " + pro);

                        pro.forEach((k, v) -> {
                                    System.out.println("k = " + k);
                                    System.out.println("v = " + v);
                                }
                        );
                    }

                    if (StrUtil.startWith(arg, "-yaml:")) {
                        String yamlStr = StrUtil.removePrefix(arg, "-yaml:");

                        System.out.println("yamlStr = " + yamlStr);

                        Dict yaml = YamlUtil.load(new StringReader(yamlStr));
                        System.out.println("yaml = " + yaml);
                        yaml.forEach((k, v) -> {

                                    System.out.println("k = " + k);
                                    System.out.println("v = " + v);
                                }

                        );
                    }
                }

            }

            //Map<String, String> getEv = System.getenv();
            //System.out.println("getEv = " + getEv);
            //
            //
            //Properties properties = System.getProperties();
            //System.out.println("properties = " + properties);

            for (int i = 0; i < 50; i++) {
                System.out.println("Hello dolphinscheduler " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}