package com.briup.environment.clientimpl;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


import com.briup.environment.bean.Environment;
import com.briup.environment.client.Gather;
import com.briup.environment.util.Configuration;
import com.briup.environment.util.ConfigurationAware;

public class GatherImpl implements Gather,ConfigurationAware {
	
	private String pathFile;
	private Configuration conf;
	
	public void setConfiguration(Configuration conf) {
		this.conf = conf;
	}
	@Override
	public void init(Properties pro) throws Exception {
		pathFile = pro.getProperty("src-file");
		//System.out.println(pathFile);
	}

	@Override
	public Collection<Environment> gather() throws Exception {
		// 读取文件，采集数据
		List<Environment> list = new ArrayList<Environment>();
		BufferedReader bReader = null;
		int count = 0;
		try {
			System.out.println(pathFile);
			bReader = new BufferedReader(new FileReader(pathFile));
			String line = null;
			// 记录读到的字节数，跳过已读字节。
			while ((line = bReader.readLine()) != null) {
				// 使用|分割
				String[] str = line.split("[|]");
				Environment env = new Environment();
				// 除了name,data 其他赋值
				env.setSrcId(str[0]);
				env.setDstId(str[1]);
				env.setDevId(str[2]);
				env.setSersorAddress(str[3]);
				env.setCount(Integer.parseInt(str[4]));
				env.setCmd(str[5]);
				env.setStatus(Integer.parseInt(str[7]));
				env.setGather_date(new Timestamp(Long.parseLong(str[8])));
				list.add(env);
				
				// 判断数据类型（温度，co2,光照）
				if (str[3].equals("16")) {
					float temp = (float) Integer.parseInt(str[6].substring(0, 4), 16);
					float value = (float) ((temp * 0.00268127) - 46.85);
					env.setName("温度");
					env.setData(value);
					count = count+2;
					Environment env2 = new Environment();
					float temp1 = (float) Integer.parseInt(str[6].substring(4, 8), 16);
					float value1 = (float) ((temp1 * 0.00190735) - 6);
					env2.setSrcId(str[0]);
					env2.setDstId(str[1]);
					env2.setDevId(str[2]);
					env2.setSersorAddress(str[3]);
					env2.setCount(Integer.parseInt(str[4]));
					env2.setCmd(str[5]);
					env2.setStatus(Integer.parseInt(str[7]));
					env2.setGather_date(new Timestamp(Long.parseLong(str[8])));
					env2.setName("湿度");
					env2.setData(value1);
					list.add(env2);
				} else if (str[3].equals("256")) {
					float strength = (float) Integer.parseInt(str[6].substring(0, 4), 16);
					env.setName("光照强度");
					env.setData(strength);
					count++;
				} else {
					float potency = (float) Integer.parseInt(str[6].substring(0, 4), 16);
					env.setName("CO2浓度");
					env.setData(potency);
					count++;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		//bReader.close();
		System.out.println(count);
		System.out.println("gather:" + list.size());
		List<Environment> ltest = new ArrayList<Environment>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
 		for (int i = 0; i < list.size(); i++) {	
 			String str = sdf.format(list.get(i).getGather_date());
			//System.out.println(str);
 			if (i == 0) {
				ltest.add(list.get(0));
			}else {
				if (!str.equals(sdf.format(list.get(i-1).getGather_date()))){
				ltest.add(list.get(i));
				}
			}
		}
		System.out.println("ltest:"+ltest.size());	
		for (Environment en : ltest) {
			System.out.println(en);
		}
		return ltest;
	}

	// main方法做测试
	public static void main(String[] args) throws Exception {
		new GatherImpl().gather();
		
	}


}
