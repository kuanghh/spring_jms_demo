package com.khh.demo1.test;

import com.khh.entity.User;
import com.khh.demo1.service.AlertService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by 951087952@qq.com on 2017/8/24.
 */
public class TestPublish {


    @Test
    public void testPublish1() throws Exception{

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        AlertService alertService = (AlertService) context.getBean("alertService");

        alertService.sendUserAlert(new User(79,"用户79",new Date()));
    }

    @Test
    public void testPublish2() throws Exception{

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        AlertService alertService = (AlertService) context.getBean("alertService");

        alertService.sendUserWithConvert(new User(79,"用户79",new Date()));
    }

}
