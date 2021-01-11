package com.fromZero.neo4jZero.a_config;

import cn.hutool.core.util.IdUtil;
import org.neo4j.ogm.id.IdStrategy;

/**
 * 自定义id的数值
 * 把默认为Long 类型 变成String（IdUtil.fastUUID()返回值类型）
 */
public class CustomIdStrategy implements IdStrategy {
    @Override
    public Object generateId(Object o) {
        return IdUtil.fastUUID();
    }
}
