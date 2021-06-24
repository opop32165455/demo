package com.from_zero.neo4j_zero.entity.relationships;


import com.from_zero.neo4j_zero.entity.node.PigNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

/**
 * @author R4441
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/11/26/026 14:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Relationships implements Serializable {


    private static final long serialVersionUID = 7832242142344366991L;
    /**
     * 关系的ID  ==  聚合、连接、属于、包括等，这些关系可能是枚举字典，因此记录关系ID是有必要的
     */
    @Id
    @GeneratedValue
    private Long relationshipID;
    /**
     * 关系名称
     */
    @Property
    private String name;
    /**
     * 关系指向哪一个节点 == 可能这个节点还有关系【节点关系递增下去】
     */
    @StartNode
    private PigNode startNode;

    @EndNode
    private PigNode endNode;
}
