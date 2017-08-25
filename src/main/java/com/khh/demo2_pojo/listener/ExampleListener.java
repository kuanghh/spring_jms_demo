package com.khh.demo2_pojo.listener;

import com.khh.entity.User;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.util.ByteSequence;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.Serializable;

/**
 * Created by 951087952@qq.com on 2017/8/24.
 */
public class ExampleListener implements MessageListener{
    @Override
    public void onMessage(Message message) {
        System.out.println("ExampleListener onMessage(Message message)");
        if (message instanceof TextMessage) {
            try {
                System.out.println(((TextMessage) message).getText());
            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }else if(message instanceof ActiveMQObjectMessage){
            ActiveMQObjectMessage objectMessage = (ActiveMQObjectMessage) message;
            try {
                User user = (User) objectMessage.getObject();
                System.out.println(user.toString());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }else if(message instanceof ActiveMQBytesMessage){
            ActiveMQBytesMessage activeMQBytesMessage = (ActiveMQBytesMessage)message;
            ByteSequence content = activeMQBytesMessage.getContent();

            System.out.println(new String(content.getData()));
        }

    }
}
