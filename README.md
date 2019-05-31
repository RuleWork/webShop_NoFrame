## 网上商城

#### 1.项目介绍

该项目是一个用Servlet/JSP+MySQL+DBUtils开发出的一套简单的网上商城，包含注册激活，分类和商品展示，购物车，下订单，后台管理等模块，项目并未采用任何框架，目的就在于巩固和加深对Servlet和JSP的理解，从而为后续学习框架打下坚实的基础。

#### 2.技术简介

- Servlet/JSP：JSP做页面展示，Servlet做业务逻辑。
- DBUtils库：替代持久层框架。
- Redis：缓存分类
- foxmail：邮件激活
- MySQL：数据库
- 易宝支付API：支付功能

#### 3.为何值得学习

- 初学者其实很多对Servlet和JSP的学习很囫囵吞枣，尤其是Servlet，Servlet几乎是所有web层框架的核心技术，例如SpringMVC就是基于Servlet来做拦截的，所以必须要扎实的掌握。
- JSP虽然可以被freemaker和thymleaf等模板引擎替代，但是也有学习的必要，毕竟很多项目用的还是JSP，其中的EL表达式，JSTL标签库选择性学习即可。
- 项目用利用反射抽取BaseServlet的那段代码非常经典，值得反复研读，SpringMVC也是基于此思想。

```java
public class abstract BaseServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// localhost:8080/store/ProductServlet?method=findProductByPid&pid=1
		String method = req.getParameter("method");

		if (null == method || "".equals(method) || method.trim().equals("")) {
			method = "execute";
		}
        
		Class clazz = this.getClass();
		try {
			Method md = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
			if(null!=md){
				String jspPath = (String)md.invoke(this, req, resp);
				if (null != jspPath) {
					req.getRequestDispatcher(jspPath).forward(req, resp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 默认方法
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return null;
	}

```

- 项目还用到了工厂和JDK动态代理进行解耦。

```java
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
```

- 还用到了Ajax和JSON与后台进行交互
- 以及简单的使用Redis对分类进行缓存。
- 对过滤器，字符处理，装饰者模式的深入理解和运用。

```java
public class PrivilegeFilter implements Filter {

    public PrivilegeFilter() {
    }

	
	public void destroy() {
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest myReq = (HttpServletRequest)request;
		//判断当前的session中是否存在已经登录成功的用户
		User user = (User)myReq.getSession().getAttribute("loginUser");
		
		if (null != user) {
			//如果存在，放行
			chain.doFilter(request, response);
		}else {
			//如果不存在，转入到提示页面
			myReq.setAttribute("msg", "请用户登录之后再去访问");
			//转发到提示页面
			myReq.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
```

#### 4.总结

- 麻雀虽小，五脏俱全，过于急功近利的去学习各种框架而没有打下坚实的JavaWeb基础，迟早是要吃亏的，所有一个完成的练手JavaWeb项目还是很有必要的！



该项目视频直接参见黑马49期网上商城项目即可，B站有资源，对照学习即可。

