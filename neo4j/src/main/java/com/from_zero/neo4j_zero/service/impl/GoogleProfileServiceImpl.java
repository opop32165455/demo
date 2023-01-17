package com.from_zero.neo4j_zero.service.impl;

import com.from_zero.neo4j_zero.entity.node.GoogleProfileNode;
import com.from_zero.neo4j_zero.neo4jDao.nodeRepository.GoogleProfileRepository;
import com.from_zero.neo4j_zero.service.GoogleProfileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangxuecheng4441
 * @date 2021/1/14/014 14:37
 */
@Service("googleProfileService")
public class GoogleProfileServiceImpl implements GoogleProfileService {
    @Resource
    private GoogleProfileRepository googleProfileRepository;

    @Override
    public GoogleProfileNode create(GoogleProfileNode profile) {
        GoogleProfileNode node = GoogleProfileNode.builder()
                .address("SKY")
                .dob("BOB")
                .address("address")
                .id(1L)
                .name("name")
                .sex("sex")
                .build();
        return googleProfileRepository.save(node);
    }

    @Override
    public void delete(GoogleProfileNode profile) {
        GoogleProfileNode node = GoogleProfileNode.builder()
                .address("SKY")
                .dob("BOB")
                .address("address")
                .id(1L)
                .name("name")
                .sex("sex")
                .build();
        googleProfileRepository.delete(node);
    }

    @Override
    public GoogleProfileNode findById(long id) {
        Optional<GoogleProfileNode> node = googleProfileRepository.findById("1");
        node.ifPresent(System.out::println);
        return null;
    }

    @Override
    public List<GoogleProfileNode> findAll() {
        Iterable<GoogleProfileNode> all = googleProfileRepository.findAll();
        System.out.println("all = " + all);
        return null;
    }
}
