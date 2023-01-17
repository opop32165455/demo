package com.from_zero.neo4j_zero.entity.node;


import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author R4441
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/11/26/026 16:23
 */


@NodeEntity(label = "person")
@NoArgsConstructor
public class PersonNode extends Node {
    private static final long serialVersionUID = 344931303506791402L;

}
