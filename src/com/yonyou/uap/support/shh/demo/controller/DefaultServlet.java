package com.yonyou.uap.support.shh.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.imooc.util.MessageUtil;

public class DefaultServlet extends HttpServlet {

	public static final String TOKEN = "weixin";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String echostr = req.getParameter("echostr");

		String[] parameters = new String[] { "timestamp", "nonce" };
		List<String> values = new ArrayList<String>();
		for (String para : parameters) {
			String value = req.getParameter(para);
			if (value != null) {
				values.add(value);
			}
		}
		values.add(TOKEN);
		Collections.sort(values);

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			sb.append(value);
		}
		String msg = resolveMessage(sb.toString());
		System.out.println(msg);
		String result = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content></xml>";
		renderResponse(resp, echostr);
	}

	private void renderResponse(HttpServletResponse resp, String msg) {
		System.out.println(msg);

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(resp.getOutputStream());
			writer.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
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
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");

			String message = null;
			if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
				if ("文档".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.firstMenu());
				} else if ("视频".equals(content)) {
					message = MessageUtil.initNewsMessage(toUserName,
							fromUserName);
				} else if ("案例".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.threeMenu());
				} else {
					message = MessageUtil.initText(toUserName, fromUserName,
							MessageUtil.threeMenu());
				}
			}
			System.out.println(message);

			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

}
