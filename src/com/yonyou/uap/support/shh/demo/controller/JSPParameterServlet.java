package com.yonyou.uap.support.shh.demo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSPParameterServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] arr={"脑残","二货","笨蛋","傻乎乎","白痴","饭桶"};
		req.setAttribute("a", arr);
		req.getRequestDispatcher("/JSPParameterTest.jsp").forward(req, resp);
	}
	
	
	



}
