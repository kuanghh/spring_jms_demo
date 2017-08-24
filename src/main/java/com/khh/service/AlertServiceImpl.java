package com.khh.service;

import com.khh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by 951087952@qq.com on 2017/8/24.
 */
@Service("alertService")
public class AlertServiceImpl implements AlertService{

    @Autowired
    private JmsOperations jmsOperations;

    public void sendUserAlert(final User user) {

        /**
         * 参数一：JMS目的的名称，标志消息接受者
         * 参数二：消息构造
         * 当jmsOperations调用send方法时，JmsTemplate将负责获得JMS连接，会话，并代表发送者发送消息
         */
        jmsOperations.send("user.queue", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                /**
                 * 在session创建了一个对象消息：传入一个user对象，返回一个对象消息
                 * 也就是该方法把对象变成了消息
                 */
                return session.createObjectMessage(user);
            }
        });
    }

    public void sendUserWithConvert(User user) {
        /**
         * 在这个方法中，不需要MessageCreator，因为该方法会使用内置的消息转换器，帮助我们把user对象转换成消息
         *
         * 默认情况下，JMSTemplate在convertAndSend()方法中会使用SimpleMessageConverter
         * 如果想将消息转换成json对象的话，那么需要在xml文件中进行配置
         */
        jmsOperations.convertAndSend("user.queue",user);
    }
}
