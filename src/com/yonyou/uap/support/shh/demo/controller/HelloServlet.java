package com.yonyou.uap.support.shh.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//	  	PrintWriter out=resp.getWriter();
//		
//		out.println("<html><head><title>HELLO</title></head><body>Hello World</body></html>");
//		
		
		req.getRequestDispatcher("/Hello.jsp").forward(req, resp);
	}

}
