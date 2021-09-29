package com.from_zero.neo4j_zero.service;

import com.from_zero.neo4j_zero.entity.node.GoogleProfileNode;

import java.util.List;


/**
 * @author zhangxuecheng4441
 * @date 2021/1/14/014 14:34
 */
public interface GoogleProfileService {
    GoogleProfileNode create(GoogleProfileNode profile);
    void delete(GoogleProfileNode profile);
    GoogleProfileNode findById(long id);
    List<GoogleProfileNode> findAll();
}
