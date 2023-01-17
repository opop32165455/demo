package com.from_zero.neo4j_zero.neo4jDao.nodeRepository;

import com.from_zero.neo4j_zero.entity.node.GoogleProfileNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangxuecheng4441
 * @date 2021/1/14/014 14:30
 */
@Repository
public interface GoogleProfileRepository extends Neo4jRepository<GoogleProfileNode, String> {
}
