package com.imooc.mall.interceptor;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.exception.UserLoginException;
import com.imooc.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return true表示继续, false表示中断
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("preHandle...");
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
        if (user == null) {
            log.info("user=null");
            throw new UserLoginException();
        }

        return true;
    }
}
