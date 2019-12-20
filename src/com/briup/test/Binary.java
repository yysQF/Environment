package com.briup.test;

import java.text.DecimalFormat;

import org.junit.Test;

public class Binary {
	@Test
	public void test_binay() {
		// 16进制转10进制
		float tempf = ((float)Integer.parseInt("123",16));
		System.out.println("123的10进制："+tempf);
		
		// 保留两位小数
		DecimalFormat dFormat = new DecimalFormat("0.00");
		System.out.println("保留两位小数："+dFormat.format(22.25687));
	}
	@Test
	public void test_jinzhi() {
		// 进制数生成
		int a = 10;
		int b = 0123;
		int c = 0x123;
		System.out.println("十进制书："+a);
		System.out.println("八进制书："+b);
		System.out.println(64+16+3);
		System.out.println("十六进制书："+c);
		System.out.println(16*16+2*16+3);
	}
	@Test
	// 测试数组复制以及值传递
	public void test_arr() {
		final String[] arr = new String[5];
		arr[0]="1";
		Object[] arr2 = new Object[5];
		// System.arraycopy(arr, 0, arr2, 0, arr.length);
		arr2 = arr ;
		arr2[2]="2";
		for (Object i : arr) {
			System.out.println(i);
		}
	}
	@Test
	// 测试正则表达式
	public void test_rex() {
		String regex = ".+-.+-.+";
		System.out.println("中国--山西南路".matches(regex));
	}
	@Test
	// 整数反转
	public void test_fanzhuan() {
		Integer a = 123;
		StringBuilder sb = new StringBuilder(a.toString());
		System.out.println(sb.reverse());
		
	}
}
