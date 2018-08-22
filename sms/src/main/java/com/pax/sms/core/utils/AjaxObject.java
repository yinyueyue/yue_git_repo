package com.pax.sms.core.utils;

import org.apache.commons.httpclient.HttpStatus;

import java.util.HashMap;

/**
 * 返回数据
 */
public class AjaxObject extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public AjaxObject(){
		put("code", "SUCCESS");
	}
	public AjaxObject put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	public static AjaxObject pageOk(Object pageInfo){
		return new AjaxObject().put("page",pageInfo);
	}
	public static AjaxObject errorMsg( String msg) {
		AjaxObject r = new AjaxObject();
		r.put("status", HttpStatus.SC_INTERNAL_SERVER_ERROR);
		r.put("msg", msg);
		r.put("code", "FAIL");
		return r;
	}
	public static AjaxObject errorCodeMsg( String errorCode,String msg) {
		AjaxObject r = new AjaxObject();
		r.put("status", HttpStatus.SC_INTERNAL_SERVER_ERROR);
		r.put("msg", msg);
		r.put("errorCode", errorCode);
		r.put("code", "FAIL");
		return r;
	}
	public static AjaxObject errorData(String msg,Object data) {
		AjaxObject r = new AjaxObject();
		r.put("status", HttpStatus.SC_INTERNAL_SERVER_ERROR);
		r.put("code","FAIL");
		r.put("msg",msg);
		r.put("data", data);
		return r;
	}
	public static AjaxObject okMsg(String msg) {
		AjaxObject r = new AjaxObject();
		r.put("status", HttpStatus.SC_OK);
		r.put("code","SUCCESS");
		r.put("msg",msg);
		return r;
	}
	public static AjaxObject okCodeMsg(String okCode,String msg) {
		AjaxObject r = new AjaxObject();
		r.put("status", HttpStatus.SC_OK);
		r.put("code","SUCCESS");
		r.put("msg",msg);
		r.put("okCode",okCode);
		return r;
	}
	public static AjaxObject okData(Object data) {
		AjaxObject r = new AjaxObject();
		r.put("status", HttpStatus.SC_OK);
		r.put("code","SUCCESS");
		r.put("data",data);
		return r;
	}

	public static AjaxObject okMsgData(String msg,Object data) {
		AjaxObject r = new AjaxObject();
		r.put("data", data);
		r.put("msg", msg);
		r.put("code", "SUCCESS");
		return r;
	}



}
