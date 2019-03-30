该项目采用Servlet/JSP+DBUtils+MySQL开发，实现了一个网上商城的基本功能，包含注册激活，
分类及商品展示，购物车，订单，后台管理等模块。

采用反射机制抽取公共的抽象类BaseServlet继承自HttpServlet重写service方法，用过滤器进行权
限验证，使用Redis缓存分类信息，抽取分页类PageModel，对商品进行分页展示，购物车利用Map结构
实现商品的添加和删除，后台模块用采用Ajax进行订单查询，用common-fileupload实现文件上传，最后
用工厂模式和动态代理模式对DAO层的方法进行解耦。

