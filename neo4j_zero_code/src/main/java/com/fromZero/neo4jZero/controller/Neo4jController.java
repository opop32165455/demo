package com.fromZero.neo4jZero.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.fromZero.neo4jZero.consts.PigFamily;
import com.fromZero.neo4jZero.neo4jDao.nodeRepository.NodeRepository;
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
