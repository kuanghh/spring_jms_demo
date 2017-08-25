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

 /*********************************下面列子可配合demo1、demo2、demo4使用*******************************************************/
    @Test
    public void testPublish1() throws Exception{

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        AlertService alertService = (AlertService) context.getBean("alertService");

        alertService.sendUserAlert(new User(79,"用户79",new Date()));
//        alertService.sendUserAlert(new User(-1,"用户79",new Date()));//如果使用了校验器，当接受到信息的时候，就会提示错误，因为id不能小于0

        Thread.sleep(3000);
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
    public void testPublishMapMessage() throws Exception{
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
    public void testPublishObjectMessage() throws Exception{
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


/*********************************下面列子配合demo4使用*******************************************************/

    /**
     * 这个方法用来测试，当消息从队列中获取后，马上把消息转发到另外一条队列，并且从此队列中获取消息
     * @throws Exception
     */
    @Test
    public void testPublishMessageForRelay() throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        AlertService alertService = (AlertService)context.getBean("alertService");

        Destination destination = (Destination) context.getBean("queue");

        alertService.sendUserObjectMessage(new User(2,"用户2",new Date()),destination);
        Thread.sleep(2000);


    }
}
