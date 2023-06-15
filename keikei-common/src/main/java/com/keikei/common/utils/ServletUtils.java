package com.keikei.common.utils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtils {

    public static void resendString(HttpServletResponse response, String msg) throws IOException {
       try {
           response.setCharacterEncoding("UTF-8");
           response.setContentType("application/json");
           response.setStatus(200);
           PrintWriter printWriter = response.getWriter();
           printWriter.println(msg);
       }
       catch (Exception e){
            e.printStackTrace();
       }
    }

    public static HttpServletRequest getRequest() {
        return getRequestAttribute().getRequest();
    }

    private static ServletRequestAttributes getRequestAttribute() {
       RequestAttributes requestAttributes =  RequestContextHolder.getRequestAttributes();
       return (ServletRequestAttributes) requestAttributes;
    }
}
