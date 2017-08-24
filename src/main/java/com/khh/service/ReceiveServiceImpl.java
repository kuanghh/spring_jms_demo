package com.khh.service;

import com.khh.entity.User;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.util.ByteSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.support.JmsUtils;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.io.Serializable;

/**
 * Created by 951087952@qq.com on 2017/8/24.
 */
@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {

    @Autowired
    private JmsOperations jmsOperations;

    @Override
    public void getMessageObject(Destination destination) {

        try {
            Message receive = jmsOperations.receive(destination);
            ActiveMQObjectMessage activeMQObjectMessage = (ActiveMQObjectMessage)receive;
            User user = (User) activeMQObjectMessage.getObject();
            System.out.println(user.toString());//User{id=79, name='用户79', birthday=Thu Aug 24 14:57:52 CST 2017}成功
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMessageWithConvert(Destination destination) {

        try {
            Message receive = jmsOperations.receive(destination);
            ActiveMQBytesMessage activeMQBytesMessage = (ActiveMQBytesMessage)receive;
            ByteSequence content = activeMQBytesMessage.getContent();

            System.out.println(new String(content.getData()));//{"id":79,"name":"用户79","birthday":1503556960814}

            //下面这两行会报NullPointerException，原因未明，不过在源码中看到，有一行代码if(name.equals("..")),这样的书写是否有错？
//            User user = (User) jmsOperations.receiveAndConvert(destination);
//            System.out.println(user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
