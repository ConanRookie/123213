package cn.com.bmsoft.baseProject.common.web;

import cn.com.bmsoft.baseProject.common.model.ucenter.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 控制器抽象类
 */
public abstract class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    /**
     * 设置属性
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /**
     * 获取用户信息
     * @return
     */
    protected UserInfo getUserInfo() {
        UserInfo userInfo;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserInfo) {
            userInfo = (UserInfo)principal;
        } else {
            userInfo = new UserInfo(principal.toString(), "");
            userInfo.setId(-1);
            userInfo.setName(principal.toString());
        }
        return userInfo;
    }

    /**
     * 获取用户名
     * @return
     */
    protected String getUsername() {
        return this.getUserInfo().getUsername();
    }
}
