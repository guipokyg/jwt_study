package com.springboot.demo.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FirstFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
       
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
    
            //  PrintWriter out = res.getWriter();
            //  res.setContentType("text/html;charset=UTF-8");
            //  out.append("<!DOCTYPE html>")
            //  .append("<head><meta charset='utf-8'></head>")
            //  .append("<body>")
            //  .append("<h1>로그인 결과 페이지</h1>")
            //  .append("</body>").append("</html>");
    
            System.out.println(req.getMethod());
           
            //post로 보내야 authorization을 받을수 있다.(헤더를 받을수 있다.)
            if("POST".equals(req.getMethod())){
                String headerAuth  = req.getHeader("Authorization");
                System.out.println(headerAuth);
    
                if("COMPL".equals(headerAuth)){
                    chain.doFilter(req,res);
                }else{
                    PrintWriter out = res.getWriter();
                    out.println("인증안됨");
                }
            }

            // chain.doFilter(req,res);
    }

    
}
