package lijianan.cms.service.impl;

import javax.annotation.Resource;

import org.junit.Test;

import com.github.pagehelper.PageInfo;
import com.lijianan.cms.domain.User;
import com.lijianan.cms.service.UserService;

public class UserServiceImplTest extends JunitParent{
	@Resource
	private UserService us;
	@Test
	public void testSelects() {
		PageInfo<User> selects = us.selects(null, 1, 2);
		System.out.println("------------"+selects);
	}

}
