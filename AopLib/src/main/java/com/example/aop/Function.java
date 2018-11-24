package com.example.aop;

/**
 * Created by xiaoqi on 2018/3/20
 */

public enum Function {

	SEARCH(1, "搜索"),
	REGISTER(2, "注册");

	int functionId;
	String functionName;

	Function(int functionId, String functionName) {
		this.functionId = functionId;
		this.functionName = functionName;
	}

	public String getFunctionName() {
		return functionName;
	}
}
