package com.from_zero.neo4j_zero.entity.node;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author zhangxuecheng4441
 * @date 2021/1/14/014 14:24
 */
@NodeEntity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleProfileNode {
    @Id
    Long id;
    private String name;
    private String address;
    private String sex;
    private String dob;

    @Override
    public String toString(){
        return "Profile[id:"+ id +",name:"+ name +",sex:" + sex+ ",address:" + address + ",dob:" + dob +"]";
    }
}