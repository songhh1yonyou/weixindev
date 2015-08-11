package com.yonyou.uap.support.shh.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.qq.weixin.mp.aes.ByteGroup;

public class AutoReply2Servlet extends HttpServlet {

	public static final String TOKEN = "weixin";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fromUser = req.getParameter("FromUserName");
		String toUser = req.getParameter("ToUserName");
		String result = "<xml><ToUserName><![CDATA["+fromUser+"]]></ToUserName><FromUserName><![CDATA["+toUser+"]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[���]]></Content></xml>";
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
		InputStream stream = req.getInputStream();
		byte[] buffer = new byte[1024];
		ByteGroup group = new ByteGroup();
		while(true){
			int l = stream.read(buffer);
			if(l<0){
				break;
			}
			for(int i=0;i<l;i++){
				group.addBytes(new byte[]{buffer[i]});
			}
		}
		String xmltext = new String(group.toBytes());
		String[] results = parse(xmltext);
		String result = "<xml><ToUserName><![CDATA["+results[1]+"]]></ToUserName><FromUserName><![CDATA["+results[0]+"]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["+results[4]+"]]></Content></xml>";
		renderResponse(resp,result);
		
	}
	
	public static String[] parse(String xml){
		List<String> results = new ArrayList<String>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		StringReader sr = new StringReader(xml);
		InputSource is = new InputSource(sr);
		try {
			Document document = db.parse(is);
			final String[] tags = new String[]{"ToUserName","FromUserName","CreateTime","MsgType","Content"};
			for (String tag:tags){
				NodeList elements = document.getElementsByTagName(tag);
				if(elements.getLength()>0){
					results.add(elements.item(0).getTextContent());
				}
			}
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results.toArray(new String[0]);
	}
	
	public static final void main(String[] args){
		String msg = " <xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content><MsgId>1234567890123456</MsgId></xml>";
		for (String str:parse(msg)){
			System.out.println(str);
		}
		
	}
}
