package com.alice.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class WriteToPage {
	
	private static final String ContentType = "text/html;charset=UTF-8";
	protected static Logger log4j = Logger.getLogger(WriteToPage.class);
	
	public static void writeToPage(HttpServletResponse response,String resultData) {
		
		String message = "";
		PrintWriter out = null;
		try {
			message = resultData;
			
			// 在使用http协议的情况中，该方法设置Content-type实体报头
			response.setContentType(ContentType);
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
		} catch (Exception e) {
			message = "";
			log4j.error(StackTraceUtil.getStackTrace(e));
		} finally {
			log4j.info("response:" + message);
			out.print(message);
			out.flush();
			out.close();
		}
	}
	public static void writeToPage2(HttpServletResponse response,String resultData) 
	{
		String message = "";
		PrintWriter out = null;
		try {
			message = resultData;
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
		} catch (Exception ex) {
			message = "";
			log4j.error(ex.getMessage());
			log4j.error(ex.getStackTrace().toString());
		}
		log4j.info("response:" + message);
		out.print(message);
		out.flush();
		out.close();
	}
	public  static void writeToPage3(HttpServletResponse response,String resultData){
		String message = "";
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter  out = response.getWriter();
			message = resultData;
			out.print(message);
			out.flush();
			out.close();
		} catch (Exception ex) {
			message = "";
			log4j.error(ex.getMessage());
			log4j.error(ex.getStackTrace().toString());
		}
		log4j.info("response:" + message);
		
	}
}
