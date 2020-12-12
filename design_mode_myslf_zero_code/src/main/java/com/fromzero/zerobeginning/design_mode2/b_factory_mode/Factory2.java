package com.fromzero.zerobeginning.design_mode2.b_factory_mode;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.fromzero.zerobeginning.design_mode2.a1_strategy_demo_ordinary.AbstractHandler;

import java.util.Map;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/12/12/012 16:28
 */
public class Factory2 {

    /**
     * 服务启动时 就生成一个存放策略方法的map （会一直占据内存，但是调用确实很快）
     */
    private static Map<String, AbstractHandler> strategyMap = MapUtil.newHashMap();

    /**
     * @param str map-key 策略方法名字
     * @return map-value 对应名字的策略方法
     */
    public static AbstractHandler getInvokeStategy(String str) {
        return strategyMap.get(str);
    }

    /**
     * 把策略方法 注册到工厂里面
     * @param str map-key 策略方法名字
     * @param abstractHandler map-value 对应名字的策略方法
     */
    public static void register(String str, AbstractHandler abstractHandler) {
        if (StrUtil.isEmpty(str) || null == abstractHandler) {
            return;
        }
        strategyMap.put(str, abstractHandler);
    }

}
