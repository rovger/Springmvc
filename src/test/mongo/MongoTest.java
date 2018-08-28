package mongo;

import com.rovger.dao.UserDAO;
import com.rovger.mybatis.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by weijlu on 2017/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class MongoTest {

	@Autowired
	private UserDAO userDao;
	private String collectionName = "user";

	@Test
	public void testAdd() {
		User user = new User("Rovger", "a111111", "Rovger@163.com", "13918159685", new Date());
		userDao.add(user, collectionName);
	}

	@Test
	public void testDel() {
		userDao.del("Lily", collectionName);
		System.out.println("=======delete successfully=======");
	}

	@Test
	public void testUpdate() {
		User user = new User("Lily", "b111111", "Lily@163.com", "13661841425", new Date());
		User newUser = (User) userDao.update(user, collectionName);
		System.out.println("============="+ newUser.getNickname());
	}

	@Test
	public void testSearch() {
		User user = (User) userDao.searchByName("Rovger", collectionName);
		System.out.println("============="+ user.getId());
	}

}
