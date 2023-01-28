package com.zero;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2020-12-12 14:28:36
 */
@RestController
@RequestMapping("test")
public class SysUserController  {


    @GetMapping("/get")
    public String test(){
        return "u success";
    }
}