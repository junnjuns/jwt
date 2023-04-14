//package com.example.jwt.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class MyFilter1 implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//
//        System.out.println("필터1");
//        chain.doFilter(request,response);
//        HttpServletRequest req = (HttpServletRequest)request;
//        HttpServletResponse res = (HttpServletResponse)response;
//
//        //예시) 토큰이름: cos
//        if (req.getMethod().equals("POST")) {
//            System.out.println("POST 요청 됨");
//            String headerAuth = req.getHeader("Authorization");
//            System.out.println(headerAuth);
//            System.out.println("필터1");
//
//            if(headerAuth.equals("cos")){
//                chain.doFilter(req,res);
//            }
//            else {
//                PrintWriter out =res.getWriter();
//                out.println("NO");
//            }
//        }
//    }
//}
