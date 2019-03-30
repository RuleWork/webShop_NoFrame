package com.itself.util;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {
	
	//解析XML
	
	public static Object createObject(String name){
		try {
			//通过传递过来的name获取application.xml中name对应的class值
			//获取到Document对象
			SAXReader reader = new SAXReader();
			//如何获取application.xml文件的输入流(application.xml必须位于src下)
			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
			Document doc = reader.read(is);
			//通过Document对象获取根节点
			Element rootElement = doc.getRootElement();
			//通过根节点获取根节点下所有的子节点bean,返回集合
			List<Element> list = rootElement .elements();
			//遍历集合，判断每个元素上的id的值 是否跟当前的name一致
			for (Element ele : list) {
				//ele相当于beans节点下的每个bean
				//获取当前节点的id值
				String id = ele.attributeValue("id");
				if (id.equals(name)) {
					//如果一致，获取到当前元素上class的属性值
					String str = ele.attributeValue("class");
					//通过反射创建对象并返回
					final Object newInstance = Class.forName(str).newInstance();
					
					if (name.endsWith("DAO")) {
						Object proxy = Proxy.newProxyInstance(newInstance.getClass().getClassLoader(), newInstance.getClass().getInterfaces(), new InvocationHandler() {
							@Override
							public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
								//如果是save方法，打印一句话
								if ("save".equals(method.getName())) {
									System.out.println("执行了dao中的保存操作");
								}
								return method.invoke(newInstance, args);
							}
						});
						return proxy;
					}
					//利用class值通过反射创建对象返回
					return newInstance;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
}
