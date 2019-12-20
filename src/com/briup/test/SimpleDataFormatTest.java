package com.briup.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.briup.environment.bean.Environment;
import com.briup.environment.client.Gather;
import com.briup.environment.clientimpl.GatherImpl;


public class SimpleDataFormatTest {
	
	@Test
	public void test() {
		Calendar c  = Calendar.getInstance();
		Date d = new Date(36000000);
		c.setTime(d);
		c.getTime();
		System.out.println(d);
		System.out.println();
	}
	public static void main(String[] args) throws Exception {

		Gather g = new GatherImpl();
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		List<Environment> list = (List<Environment>) g.gather();
		System.out.println(list.size());
		for (Environment envir : list) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd");
			Timestamp ts = envir.getGather_date();
			String i = sdf.format(ts);

			int count = 1;
			if (!hm.containsKey(i)) {
				hm.put(i, count);
			} else {
				hm.put(i, hm.get(i) + 1);
			}
		}
		Set<Entry<String, Integer>> set = hm.entrySet();
		for (Entry<String, Integer> entry : set) {
			System.out.println(entry);
		}
		System.out.println(hm.size());

//		System.out.println("插入成功");
//		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date myDate2 = dateFormat2.parse("2010-09-13 22:36:01");
//		SimpleDateFormat sdf = new SimpleDateFormat("dd");
//		String string = sdf.format(myDate2);
//		System.out.println(string);
	}
}
