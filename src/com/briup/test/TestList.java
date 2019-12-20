package com.briup.test;

import java.util.ArrayList;
import java.util.List;

import com.briup.environment.bean.Environment;

public class TestList {
	public static void main(String[] args) {
		List<Environment> list = new ArrayList<Environment>();
		Environment environment = new Environment();
		
		list.add(environment);
		environment.setName("1231231");
		
		System.out.println(environment);
		System.out.println(list.get(0));
	}
}
