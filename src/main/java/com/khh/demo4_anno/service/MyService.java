package com.khh.demo4_anno.service;

import com.khh.entity.User;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * Created by 951087952@qq.com on 2017/8/25.
 *
 * 下面方法最后只存在一个，因为它们的参数类型有些是差不多的，spring都能识别到，并将其转换，
 * 所以在测试的过程中，下面方法最好只存在一个
 *
 */
@Component
public class MyService {

    /**
     * @JmsListener 注解依赖于java8
     *
     *      使用@JmsListener注解可以很方便的用来consume消息，
     *      但是它的性能非常低下，因为它是synchronous得consume消息，而不是asynchronous得consume消息
     * @param text
     */
//    @JmsListener(destination = "user.queue")
    public void processOrder(String text){
        System.out.println("MyService processOrder(String text)");
        System.out.println(text);
    }

//    @JmsListener(destination = "user.queue")
    public void processOrder(Serializable obj){
        System.out.println("MyService processOrder(Serializable obj)");
        System.out.println(obj.toString());
    }

    /**
     * @Header 的作用是，把一些JMS的Header信息，注入到方法参数中，详细信息，请看JmsHeaders.java
     *
     *  除了Header信息以外，注入的信息可以有很多，
     *       Session，Message都可以注入，详情请看spring 的官方文档
     * @param user
     * @param JMSDestination
     */
//    @JmsListener(destination = "user.queue")
    public void processOrder(User user, @Header(JmsHeaders.DESTINATION) String JMSDestination){
        System.out.println("MyService processOrder(User user)");
        if(user != null){
            System.out.println(user.toString());
        }
        if(JMSDestination == null){
            System.out.println("orderHeader == null");
        }else{
            System.out.println("orderHeader = " + JMSDestination);
        }

    }

    /**
     * @Validated 声明对指定参数做校验，这里需要配置校验器，详细请看SpringJMSCONf
     * @param user
     * @param
     */
//    @JmsListener(destination = "user.queue")
    public void processOrder(@Validated User user){
        System.out.println("MyService processOrder(User user)");
        if(user != null){
            System.out.println(user.toString());
        }
    }

    /*****************************************Together***********************************************************/
    /**
     * 这个属于转发消息，当消息从队列user.queue获取后，转发到user.queue.new队列中
     * @param user
     * @return
     */
    @JmsListener(destination = "user.queue")
    @SendTo("user.queue.new")
    public User process(User user){
        System.out.println("MyService process(User user)");
        System.out.println(user.toString());
        return user;
    }

    @JmsListener(destination = "user.queue.new")
    public void processFromQueue(User user,@Header(JmsHeaders.DESTINATION) String destination){
        System.out.println("MyService processFromQueue(User user,@Header(JmsHeaders.DESTINATION) String destination)");
        System.out.println("user : " + user.toString());
        System.out.println("destination : " + destination);
    }
    /*******************************************Together*********************************************************/

}
