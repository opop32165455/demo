package com.from_zero.neo4j_zero.entity.node;


import com.from_zero.neo4j_zero.a_config.CustomIdStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/11/27/027 9:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity(label = "pig")
@Builder
public class Node implements Serializable {
    /**
     * @Id： 实体主键
     * @GeneratedValue： 实体属性值自增
     */
    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    @Labels
    private List<String> labels = new ArrayList<>();
    /**
     *  节点名字
     */
    @Property
    private String name;
    @Property
    private Integer age;
    @Property
    private String gender;


}
