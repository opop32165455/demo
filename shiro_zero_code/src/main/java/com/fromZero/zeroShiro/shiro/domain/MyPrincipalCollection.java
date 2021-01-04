package com.fromZero.zeroShiro.shiro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * @author zhangxuecheng4441
 * @date 2021/1/4/004 20:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPrincipalCollection extends SimplePrincipalCollection {

    private static final long serialVersionUID = -1606915717396567656L;

    private String infoCustomAttribute1;

    private String infoCustomAttribute2;
}
