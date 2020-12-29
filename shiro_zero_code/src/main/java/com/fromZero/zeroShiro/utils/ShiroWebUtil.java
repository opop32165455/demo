package com.fromZero.zeroShiro.utils;


import com.fromZero.zeroShiro.consts.AuthResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lin
 * @since 2017/4/7.
 */
public class ShiroWebUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ShiroWebUtil.class);

    /**
     * 是否是Ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request){
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

    /**
     * response 输出JSON
     * @param baseResponse
     */
    public static void output(ServletResponse response, AuthResponse baseResponse){
        Gson gson = new Gson();
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            Map<String,Object> res = new LinkedHashMap<>();
            if(baseResponse == AuthResponse.NO_LOGIN){
                res.put("code", -1);
            }else {
                res.put("code", 1);
            }
            res.put("msg", baseResponse.getMsg());
            out.println(gson.toJson(res));
        } catch (Exception e) {
            LOG.error("error to output json!", e);
        }finally{
            if(null != out){
                out.flush();
                out.close();
            }
        }
    }
}
