package com.briup.environment.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.briup.environment.client.Client;
import com.briup.environment.client.Gather;
import com.briup.environment.server.DBStore;
import com.briup.environment.server.Server;

public class ConfigurationImpl implements Configuration {
	// 定义只能存放其他模块对象的map
	private Map<String, WossModuleInit> map = new HashMap<String, WossModuleInit>();

	public ConfigurationImpl() throws Exception {
		this("file/config.xml");
	}

	public ConfigurationImpl(String path) throws Exception {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(path);
		} catch (DocumentException e1) {

			e1.printStackTrace();
		}
		Element root = doc.getRootElement();
		//System.out.println(root.getName());

		List<Element> allChild = root.elements();
		for (Element temp : allChild) {
			String clz = temp.attributeValue("class");
			// 通过反射获取
			WossModuleInit o = (WossModuleInit) Class.forName(clz).newInstance();
			map.put(temp.getName(), o);
			// 调用init 传入数据
			Properties pro = new Properties();
			List<Element> pros = temp.elements();
			for (Element p : pros) {
				pro.put(p.getName(), p.getText());
			}
			o.init(pro);
			System.out.println(pro);
		}
		// 将自身的引用this 注入给其他模块
		for (WossModuleInit woss : map.values()) {
			if(woss instanceof ConfigurationAware) {
				((ConfigurationAware)woss).setConfiguration(this);
			}
		}
		System.out.println("== 数据成功初始化 ==");
	}

	@Override
	public Client getClient() throws Exception {

		return (Client) map.get("client");
	}

	@Override
	public DBStore getDbStore() throws Exception {

		return (DBStore) map.get("dbstore");
	}

	@Override
	public Gather getGather() throws Exception {

		return (Gather) map.get("gather");
	}

	@Override
	public Log getLogger() throws Exception {

		return (Log) map.get("logger");
	}

	@Override
	public Server getServer() throws Exception {

		return (Server) map.get("server");
	}

	public static void main(String[] args) throws Exception {
		new ConfigurationImpl();
	}

}
