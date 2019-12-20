package com.briup.environment.Test;

import com.briup.environment.client.Client;
import com.briup.environment.client.Gather;
import com.briup.environment.clientimpl.GatherImpl;
import com.briup.environment.util.Configuration;
import com.briup.environment.util.ConfigurationImpl;

public class ClientTest {
	public static void main(String[] args) throws Exception {
		Configuration conf = new ConfigurationImpl();
		Gather g = conf.getGather();
		Client c = conf.getClient();
		System.out.println("== 数据开始发送 ==");
		c.send(g.gather());
		
	}
}
