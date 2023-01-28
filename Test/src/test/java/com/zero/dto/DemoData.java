package com.zero.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;

/**
 * @author zhangxuecheng4441
 * @date 2022/2/17/017 13:55
 */
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@NoArgsConstructor
public class DemoData{
    @ExcelProperty(index = 0,value = "user_id")
    private String id;
    @ExcelProperty(index = 1,value = "nickname")
    private String name1;
    @ExcelProperty(index = 2, value = "user_id")
    private String name2;
    @ExcelProperty(index = 3, value = "user_id")
    private String url;

}
