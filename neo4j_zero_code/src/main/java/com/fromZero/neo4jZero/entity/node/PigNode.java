package com.fromZero.neo4jZero.entity.node;


import com.fromZero.neo4jZero.entity.relationships.Relationships;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author R4441
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/11/26/026 14:41
 */


@NodeEntity(label = "pig")
@NoArgsConstructor
public class PigNode extends Node  {

    private static final long serialVersionUID = 784222662907194037L;

    /**
     * 节点里面是否包含有边 == 关系
     */
    @Relationship(type = "父子")
    private Relationships relationships;
}
