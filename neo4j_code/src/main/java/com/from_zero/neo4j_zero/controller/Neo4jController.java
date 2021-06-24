package com.from_zero.neo4j_zero.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.from_zero.neo4j_zero.consts.PigFamily;
import com.from_zero.neo4j_zero.neo4jDao.nodeRepository.NodeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangxuecheng4441
 * @date 2021/1/11/011 20:21
 */
@RestController
@RequestMapping
public class Neo4jController extends ApiController {

    @Resource
    NodeRepository nodeRepository;


    @GetMapping("/selectByName")
    public R selectByName() {
        return success(nodeRepository.findByName(PigFamily.ZHU_PEI_QI));
    }
}
