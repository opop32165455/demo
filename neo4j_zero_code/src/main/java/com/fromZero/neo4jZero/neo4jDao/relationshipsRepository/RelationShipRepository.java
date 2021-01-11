package com.fromZero.neo4jZero.neo4jDao.relationshipsRepository;


import com.fromZero.neo4jZero.entity.relationships.Relationships;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Desciption:
 * @Auther: ZhangXueCheng4441
 * @Date:2020/11/26/026 15:35
 */
@Repository
public interface RelationShipRepository extends Neo4jRepository<Relationships,Long> {
    @Query("match (a :pig{name:{name1}}),(b :pig{name:{name2}}),(a)-[r]->(b) return r")
    Relationships getPigRelastionships(@Param("name1") String name1, @Param("name2") String name2);
}
