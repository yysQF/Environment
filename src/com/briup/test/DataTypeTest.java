package com.briup.test;

public class DataTypeTest {
	public static void main(String[] args) {
		String str = "123456";
		int a = Integer.parseInt(str.substring(0,2));
		Float b =a*0.2f - 6;
		if(b instanceof Float) {
			System.out.println(" 一致 ");
		}
	}
}
