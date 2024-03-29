package com.from_zero.neo4j_zero.neo4jDao.nodeRepository;


import com.from_zero.neo4j_zero.entity.node.PersonNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/11/26/026 16:27
 */
@Repository
public interface PersonRepository extends Neo4jRepository<PersonNode,String> {
   /* @Query("MATCH (n:person) RETURN n ")
    List<PersonNode> getPersonNodeList();*/
}
