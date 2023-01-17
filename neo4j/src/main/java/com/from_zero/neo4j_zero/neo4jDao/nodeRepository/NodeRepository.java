package com.from_zero.neo4j_zero.neo4jDao.nodeRepository;

import com.from_zero.neo4j_zero.entity.node.Node;
import com.from_zero.neo4j_zero.entity.node.PigNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/11/26/026 15:04
 */
@Repository
public interface NodeRepository extends Neo4jRepository<Node, String> {
    //根据name直接查节点信息
    List<Node> findByName(String name);

    @Query("MATCH (n:pig) RETURN n ")
    List<PigNode> getPigNodeList();

    @Query("create (n:User{age:{age},name:{name}}) RETURN n ")
    List<PigNode> addUserNodeList(@Param("name") String name, @Param("age") int age);

    @Query("match (a) where id(a)={id} delete a return a")
    void deleteNodeById(@Param("id") Integer id);

}
