package com.fromZero.zero2.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fromZero.zero2.entity.SysUser;
import com.fromZero.zero2.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2020-12-12 14:28:36
 */
@Controller
@RequestMapping("sysUser")
public class SysUserController extends ApiController {


    @GetMapping("/getJson")
    @ResponseBody
    public void urlToJson(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setCharacterEncoding("utf-8");
        //设置响应内容的类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        response.addHeader(
                "Content-Disposition",
                "attachment; filename="
                        +  "JSON_FOR_UCC_"
                        + ".txt");//通过后缀可以下载不同的文件格式
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        // 让浏览器用UTF-8解析数据
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write("delNull(jsonString)".getBytes(StandardCharsets.UTF_8));
            buff.flush();
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert buff != null;
            buff.close();
            outStr.close();
        }
    }


    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param sysUser 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysUser> page, SysUser sysUser) {
        return success(this.sysUserService.page(page, new QueryWrapper<>(sysUser)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysUserService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysUser 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysUser sysUser) {
        return success(this.sysUserService.save(sysUser));
    }

    /**
     * 修改数据
     *
     * @param sysUser 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysUser sysUser) {
        return success(this.sysUserService.updateById(sysUser));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.sysUserService.removeByIds(idList));
    }
}