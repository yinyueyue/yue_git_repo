package com.pax.sms.core.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author Bill
 *
 */
public class DateUtils {
	private static  final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	public static final String LONG_FORMAT		= "yyyyMMddHHmmss";
	public static final String SHORT_FORMAT	= "yyyyMMdd";
	public static final String LONG_FORMAT_YY	= "yyMMddHHmmss";
	public static final String SHORT_FORMAT_SEP = "yyyy/MM/dd";
	public static final String LONG_FORMAT_SEP = "yyyy/MM/dd HH:mm:ss";
	public static final String LONG_FULL_FORMAT_SEP = "yyyy-MM-dd HH:mm:ss";


	public static String toLongFormat(String dateTime)throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(LONG_FULL_FORMAT_SEP);
		Date date = sdf.parse(dateTime);

		return date2String(date,LONG_FORMAT);
	}


	/**
	 * 日期转字符
	 * 
	 * @param date
	 *            ：待转的日期
	 * @param format
	 *            ：转换格式，如yyyy-mm-dd,为空采用yyyymmddhhMMss
	 * @return: 转换后的字符串形式
	 */
	public static String date2String(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			logger.info("格式化字符日期失败[data:" + date + ", format:" + format + "]");
			return null;
		}
		
	}
	
	/**
	 * 日期转字符
	 * 
	 * @param date
	 *            ：待转的日期
	 * @return: 转换后的字符串形式yyyyMMddHHmmss
	 */
	public static String date2String(Date date) {
		return date2String(date, "yyyyMMddHHmmss");
	}
	
	/**
	 * 将从数据库中取出的字符串形式的日期格式化
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static String stringDateFormat(String dateString, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date tmpdate = sdf.parse(dateString);
			sdf = new SimpleDateFormat(format);
			return sdf.format(tmpdate);
		} catch (Exception e) {
			logger.error("转换日期出错");
			return null;
		}
	}
	
	/**
	 * 将指定格式的日期字符串转成日期
	 * 
	 * @param dateStr
	 *            待转的日期字符串
	 * @param format
	 *            待转的日期字符串格式
	 * @return 日期
	 */
	public static Date string2date(String dateStr, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 将日期转成指定格式的字符串
	 * 
	 * @param date
	 *            待转的日期
	 * @param format
	 *            转换字符串的格式
	 * @return 转换后的字符串
	 */
	public static String date2string(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 日期字符串格式转化
	 * 
	 * @param dateString
	 *            待转换的日期字符串
	 * @param src_format
	 *            待转换的日期字符串格式
	 * @param des_format
	 *            转换后的格式
	 * @return 转换后的日期字符串
	 */
	public static String stringDateFormat(String dateString, String src_format, String des_format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(src_format);
			Date tmpdate = sdf.parse(dateString);
			sdf = new SimpleDateFormat(des_format);
			return sdf.format(tmpdate);
		} catch (Exception e) {
			//e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) {
		String s = stringDateFormat("20170802","yyyyMMdd","yyyy-MM-dd");
		System.out.println(s);
	}


	
	public static String getCurrentDateString() {
		return DateUtils.date2String(new Date());
	}
}
