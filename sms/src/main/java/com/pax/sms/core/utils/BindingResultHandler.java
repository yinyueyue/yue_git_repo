package com.pax.sms.core.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class BindingResultHandler {
	
	public static JSONObject handleBindingResult(BindingResult br) {
		
		List<FieldError> list = br.getFieldErrors();
		
		JSONObject jsonObject = new JSONObject();
		
		for (int i = 0; i < list.size(); i++) {
			FieldError fieldError = list.get(i);
			jsonObject.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return jsonObject;
		
	}
}
