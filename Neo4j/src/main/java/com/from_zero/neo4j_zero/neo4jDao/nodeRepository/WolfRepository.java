package com.from_zero.neo4j_zero.neo4jDao.nodeRepository;


import com.from_zero.neo4j_zero.entity.node.WolfNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/11/26/026 16:26
 */
@Repository
public interface WolfRepository extends Neo4jRepository<WolfNode,String> {
  /*  @Query("MATCH (n:wolf) RETURN n ")
    List<WolfNode> getWolfNodeList();*/
}
