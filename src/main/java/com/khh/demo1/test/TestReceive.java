package com.khh.demo1.test;

import com.khh.demo1.service.ReceiveService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.Destination;

/**
 * Created by 951087952@qq.com on 2017/8/24.
 */
public class TestReceive {

    @Test
    public void testReceive1() throws Exception{

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ReceiveService receiveService = (ReceiveService) context.getBean("receiveService");

        Destination destination = (Destination) context.getBean("queue");
        receiveService.getMessageObject(destination);
    }

    @Test
    public void testReceive2() throws Exception{

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ReceiveService receiveService = (ReceiveService) context.getBean("receiveService");

        Destination destination = (Destination) context.getBean("queue");
        receiveService.getMessageWithConvert(destination);
    }
}
