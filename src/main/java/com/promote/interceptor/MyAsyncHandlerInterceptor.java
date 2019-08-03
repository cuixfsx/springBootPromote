package com.promote.interceptor;

import com.promote.controller.AsyncController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyAsyncHandlerInterceptor implements AsyncHandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(MyAsyncHandlerInterceptor.class);

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(Thread.currentThread().getName() + " 拦截到后重写数据");
        String resp = "改写数据";
        response.setContentLength(resp.length());
        response.getOutputStream().write(resp.getBytes());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info(Thread.currentThread().getName() + " 调用完成");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (null != ex) {
            logger.info(Thread.currentThread().getName() + " 发送异常"+ex.getMessage());
        }
    }
}
