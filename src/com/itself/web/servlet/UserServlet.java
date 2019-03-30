package com.itself.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.itself.bean.User;
import com.itself.service.IUserService;
import com.itself.service.impl.UserServiceImpl;
import com.itself.util.MailUtils;
import com.itself.util.MyBeanUtils;
import com.itself.util.UUIDUtils;
import com.itself.web.base.BaseServlet;


public class UserServlet extends BaseServlet {

	public String regisUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "/jsp/register.jsp";
	}
	
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "/jsp/login.jsp";
	}
	
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//接收表单参数
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		MyBeanUtils.populate(user, map);
		//为用户的其他属性赋值
		user.setUid(UUIDUtils.getId());//随机生成一个唯一的Id
		user.setState(0);//是否被激活了 0没，1有
		user.setCode(UUIDUtils.getCode());//激活码
		
		System.out.println(user);
		
		//调用业务层注册功能
		IUserService userService = new UserServiceImpl();
		try {
			userService.userRegist(user);
			//注册成功，向用户邮箱发送信息，跳转到提示页面
			//发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "用户注册成功，请激活！");
		} catch (Exception e) {
			//注册失败，跳转到提示页面
			request.setAttribute("msg", "用户注册失败，请重新注册！");
		}
		return "/jsp/info.jsp";
	}

	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取激活码
		String code = request.getParameter("code");
		//调用业务层的激活功能
		IUserService userService = new UserServiceImpl();
		boolean flag = userService.userActive(code);
		//进行激活信息提示 
		if (flag == true) {
			//用户激活成功，向request放入提示信息,转发到登录页面
			request.setAttribute("msg", "用户激活成功，请登录！");
			return "/jsp/login.jsp";
		}else {
			//用户激活失败，向request放入提示信息,转发到提示页面
			request.setAttribute("msg", "用户激活失败，请重新激活！");
			return "/jsp/info.jsp";
		}
		//激活信息提示
	}
	
	//userLogin
	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取用户数据(账号、密码)
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		//调用业务层的登录功能
		IUserService userService = new UserServiceImpl();
		User user_new = null;
		try {
			user_new = userService.userLogin(user);
			//用户登录成功,将用户信息放入session
			request.getSession().setAttribute("loginUser", user_new);
			response.sendRedirect("/store_v1/index.jsp");
			return null;
		} catch (Exception e) {
			//用户登录失败
			String msg = e.getMessage();
			System.out.println(msg);
			//向request放入登录失败的信息
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
	}
	
	//userLogout
	public String userLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//清楚session
		request.getSession().invalidate();
		//重定向到首页
		response.sendRedirect("/store_v1/index.jsp");
		return null;
	}
}
