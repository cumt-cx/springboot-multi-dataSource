package com.lovepi.sandwich;

import com.lovepi.sandwich.entity.User;
import com.lovepi.sandwich.enums.UserSexEnum;
import com.lovepi.sandwich.mapper.test1.User1Mapper;
import com.lovepi.sandwich.mapper.test2.User2Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by cumt_cx on 2017/1/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class multiDataSourceTest {

    @Autowired
    private User1Mapper user1Mapper;

    @Autowired
    private User2Mapper user2Mapper;

    @Test
    public void testUser1MapperAddMethod(){
        User user = new User("fuq","123456", UserSexEnum.MAN,"bigMan");
        user1Mapper.insert(user);
    }

    @Test
    public void testUser2MapperAddMethod(){
        User user = new User("fuq","123456", UserSexEnum.MAN,"bigMan");
        user2Mapper.insert(user);
    }

}
