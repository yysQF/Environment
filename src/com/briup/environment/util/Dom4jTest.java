package com.briup.environment.util;

import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jTest {
	public static void main(String[] args) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read("file/config.xml");
		Element root = doc.getRootElement();
		System.out.println(root.getName());
		System.out.println("获取到根元素");
		List<Element> allChild = root.elements();
		for (Element temp : allChild) {
			String name = temp.getName();
			// 服务器端
			if ("server".equals(name)) {
				String o = temp.attributeValue("class");
				Element eport = temp.element("port");
				String port = eport.getText();
				System.out.println(o);
				System.out.println(o instanceof String);
				System.out.println("获取到子元素");
				System.out.println(port);
			}
		}
	}
}
