package com.rovger.reources;

import com.google.gson.Gson;
import com.rovger.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weijlu on 2017/3/27.
 * Jersey 2.X实现Restful API
 * @Component：表示这个类由Spring的IoC容器来管理，SpringMVC中使用的@Controller；
 @AutoWired：表示Spring的自动装配，将需要依赖的类直接装配进来，当然本例子没有使用到，具体项目中一定会需要；
 @Path("/index")：该注解标记在类名之上，表示这是一个根资源；类似于SpringMVC中的@RequestMapping("/index")；
 @Path("/student")：该注解标记在方法名之上，表示这是一个子资源，通过/student来寻找具体方法调用；
 @GET：该注解表示被标记的方法提供的是一个GET请求，同样还有@POST、@PUT、@DELET、@PUTCH等注解；
 @Produces({MediaType.xxx})：该注解表示以json或者xml文件的形式进行输出，类似于SpringMVC中的@ResponseBody注解；
 当然还有@QueryParam，@Context等等注解，后面会专门写一篇关于Jersey和SpringMVC之间区别和联系的文章，敬请期待。
 */
@Component
@Path("/v1")
public class HelloWorldService {
	private static final Logger logger = LoggerFactory.getLogger(HelloWorldService.class);
	Gson gson = new Gson();

	@GET
	@Path("/info")
	@Produces({MediaType.APPLICATION_JSON})
	public String getInfo() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name","Rovger");
		map.put("age", "26");
		logger.info("getInfo_info has been called.");
		return gson.toJson(map);
	}

	@POST
	@Path("/add")
	public String addInfo(String str) {
		Student student = gson.fromJson(str, Student.class);
		System.out.println("name:"+student.getName()+"=====age:"+student.getAge());
		return str;
	}
}
