package com.springmvc.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommonFilter extends HttpFilter {

    private final Logger log = LoggerFactory.getLogger(CommonFilter.class);

    /**
     * 抽象方法，为http请求定制，必须实现的方法
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("======START========拦截get请求================");
        System.out.println("======START========拦截get请求================");
        if("GET".equalsIgnoreCase(request.getMethod())){
            RequestUtil.saveRequest(request);
        }
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        System.out.println("url:" + url);
        if("/login".equals(url) || url.indexOf("/js") != -1
                || url.indexOf("/css") != -1 || url.indexOf("/images") != -1
                || url.indexOf("/doLogin") != -1 || url.indexOf("/doRegister") != -1
        ){
            filterChain.doFilter(request,response);
            System.out.println("ok:不处理拦截");
        }
        else {
            System.out.println("ok:做处理拦截");
            String username = (String) request.getSession().getAttribute("ihvke");
            if(username == null){
                log.info("被拦截：跳转到login页面！");
//                System.out.println("被拦截：跳转到login页面！");
                request.getRequestDispatcher("/WEB-INF/html/login.html").forward(request,response);
            }else {
                if("/".equals(url)){
                    request.getRequestDispatcher("/WEB-INF/html/Home.html").forward(request,response);
                }
                filterChain.doFilter(request,response);
            }
        }
        System.out.println("======END========拦截get请求================");
    }
}
