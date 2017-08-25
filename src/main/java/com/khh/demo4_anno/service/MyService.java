package com.khh.demo4_anno.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by 951087952@qq.com on 2017/8/25.
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
    @JmsListener(destination = "user.queue")
    public void processOrder(String text){
        System.out.println("MyService processOrder(String text)");
        System.out.println(text);
    }

    @JmsListener(destination = "user.queue")
    public void processOrder(Serializable obj){
        System.out.println("MyService processOrder(Serializable obj)");
        System.out.println(obj.toString());
    }
}
