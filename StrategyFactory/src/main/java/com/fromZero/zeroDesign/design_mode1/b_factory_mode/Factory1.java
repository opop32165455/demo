package com.fromZero.zeroDesign.design_mode1.b_factory_mode;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.fromZero.zeroDesign.design_mode1.a1_strategy_demo_ordinary.Handler;

import java.util.Map;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 16:28
 */
public class Factory1 {

    /**
     * 服务启动时 就生成一个存放策略方法的map （会一直占据内存，但是调用确实很快）
     */
    private static Map<String, Handler> strategyMap = MapUtil.newHashMap();

    /**
     * @param str map-key 策略方法名字
     * @return map-value 对应名字的策略方法
     */
    public static Handler getInvokeStategy(String str) {
        return strategyMap.get(str);
    }

    /**
     * 把策略方法 注册到工厂里面
     * @param str map-key 策略方法名字
     * @param handler map-value 对应名字的策略方法
     */
    public static void register(String str, Handler handler) {
        if (StrUtil.isEmpty(str) || null == handler) {
            return;
        }
        strategyMap.put(str, handler);
    }

}
