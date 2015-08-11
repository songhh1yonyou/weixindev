package com.yonyou.uap.support.shh.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AutoReplyServlet extends HttpServlet {

	public static final String TOKEN = "weixin";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fromUser = req.getParameter("FromUserName");
		String toUser = req.getParameter("ToUserName");
		String result = "<xml><ToUserName><![CDATA["+fromUser+"]]></ToUserName><FromUserName><![CDATA["+toUser+"]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[ÄãºÃ]]></Content></xml>";
		renderResponse(resp,result);
	}

	private void renderResponse(HttpServletResponse resp,String msg) {
		System.out.println(msg);
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(resp.getOutputStream());
			writer.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private String resolveMessage(String str) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(str.getBytes());
			result = new String(digest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
