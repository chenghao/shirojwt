package com.hao.shirojwt.util;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", HttpStatus.OK.value());
		put("msg", "操作成功");
		put("data", null);
	}

	public static R error() {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "操作失败");
	}

	public static R error(String msg) {
		return error().put("msg", msg);
	}

	public static R error(int code, String msg) {
		return custom(code, msg, null);
	}

	public static R error(int code, String msg, Object data) {
		return custom(code, msg, data);
	}


	public static R custom(int code, String msg, Object data){
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		r.put("data", data);
		return r;
	}


	public static R ok(Object data) {
		R r = ok();
		r.put("data", data);
		return r;
	}

	public static R ok(int code, String msg){
		return ok(code, msg, null);
	}

	public static R ok(int code, String msg, Object data){
		return custom(code, msg, data);
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

}
