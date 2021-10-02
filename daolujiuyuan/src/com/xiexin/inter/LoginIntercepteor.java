package com.xiexin.inter;


import com.xiexin.bean.Customerservice;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// session 拦截器.
public class LoginIntercepteor implements HandlerInterceptor {
@Override
public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
// 1. 看是否有session
HttpSession session = httpServletRequest.getSession();
    Customerservice loginUser = (Customerservice) session.getAttribute("dbAccount");
// 2. 判断 这个 loginUser 是否为空
if (loginUser != null) {
return true; // 放行
}
// 重定向到  登录页
//httpServletRequest.getRequestDispatcher("/res/login.html").forward(httpServletRequest, httpServletResponse);
return false;  // 拦截
}

@Override
public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

}

@Override
public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

}



//  下面代码 拷贝到   spring.xml 的  代码中
//
//  	<!--配置拦截器-->
//  	<mvc:interceptors>
//       		<mvc:interceptor>
//          			<!--拦截的网址-->
//         			<mvc:mapping path="/**"/>
//          			<!--不拦截的网址-->
//          			<mvc:exclude-mapping path="/login_ui" />
//           			<mvc:exclude-mapping path="/用户的实体类的类名小写/login" />
//          			<mvc:exclude-mapping path="/用户的实体类类名小写/logOut" />
//        			<!--拦截器的类-->
//         			<bean class="com.xiexin.intecepter.LoginIntercepteor" />
//         		</mvc:interceptor>
//     	</mvc:interceptors>

}
