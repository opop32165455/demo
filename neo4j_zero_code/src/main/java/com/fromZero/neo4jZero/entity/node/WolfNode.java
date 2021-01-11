package com.fromZero.neo4jZero.entity.node;


import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/11/26/026 16:22
 */

@NodeEntity(label = "wolf")
@NoArgsConstructor
public class WolfNode extends Node {

    private static final long serialVersionUID = 4743532647900880241L;


}
