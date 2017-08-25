package com.khh.test;

import com.khh.entity.User;
import com.khh.demo1.service.AlertService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.Destination;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

/*********************************下面列子配合demo3使用*******************************************************/
    @Test
    public void testPublishTextMessage() throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        AlertService alertService = (AlertService) context.getBean("alertService");

        Destination destination = (Destination)context.getBean("queue");
        alertService.sendUserTextMessage("这是一个字符串",destination);
    }

    @Test
    public void testPublishByteMessage() throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        AlertService alertService = (AlertService)context.getBean("alertService");

        Destination destination  = (Destination) context.getBean("queue");
        String str = "123456789987654321";
        alertService.sendUserByteMessage(str.getBytes(),destination);
    }

    @Test
    public void testPublicMapMessage() throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        AlertService alertService = (AlertService)context.getBean("alertService");

        Destination destination = (Destination) context.getBean("queue");
        Map<String,Object> map = new HashMap<>();

        map.put("int",1);
        map.put("char",'c');
        map.put("String","string");
//        map.put("Object",new User(78," 用户78",new Date()));//这里会报错，因为MapMesssage只能接收java的primitive类型数据，也就是基本数据类型
//        如果想放置更多的东西，可以尝试使用ObjectMessage 传递，传递一个map即可,看最后一个方法
        alertService.sendUserMapMessage(map,destination);
    }

    @Test
    public void testPublicObjectMessage() throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        AlertService alertService = (AlertService)context.getBean("alertService");

        Destination destination = (Destination) context.getBean("queue");
        Map<String,Object> map = new HashMap<>();

        map.put("int",1);
        map.put("char",'c');
        map.put("String","string");
        map.put("Object",new User(78," 用户78",new Date()));

        alertService.sendUserObjectMessage(map,destination);
    }

}
