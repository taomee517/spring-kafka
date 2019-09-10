package com.demo.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = (String)request.getSession().getAttribute("user");
        boolean flag = StringUtils.isEmpty(username);
        if(flag){
            log.info("请求被拦截,访问地址:{}",request.getRequestURL());
            request.getSession().setAttribute("msg","您还没有登录,请先登录!");
            // response.sendRedirect("/index.html");
            request.getRequestDispatcher("/index.html").forward(request,response);
            return !flag;
        }else{
            log.info("放行请求,访问地址:{}",request.getRequestURL());
            return !flag;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
