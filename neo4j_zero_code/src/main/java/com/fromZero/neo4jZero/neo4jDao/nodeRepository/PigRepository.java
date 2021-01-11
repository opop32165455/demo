package com.fromZero.neo4jZero.neo4jDao.nodeRepository;

import com.fromZero.neo4jZero.entity.node.PigNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/11/28/028 18:58
 */
public interface PigRepository extends Neo4jRepository<PigNode,String> {
}
