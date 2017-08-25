package com.khh.demo1.service;

import com.khh.entity.User;

import javax.jms.Destination;
import java.util.Map;

/**
 * Created by 951087952@qq.com on 2017/8/24.
 *
 *  发送消息服务接口
 */
public interface AlertService {

    /**
     * 这个方法作用是：将User对象变成消息发送出去
     *
     * @param user
     */
    void sendUserAlert(User user);


    /**
     * 这个方法同上，只不过简化了代码
     * @param user
     */
    void sendUserWithConvert(User user);


    /**
     * 发送一个文本消息
     */
    void sendUserTextMessage(String message, Destination destination);


    /**
     * 发送字节消息
     */
    void sendUserByteMessage(byte[] message,Destination destination);

    /**
     * 发送Map消息
     */
    void sendUserMapMessage(Map<String,Object> message,Destination destination);

    /**
     * 发送对象消息
     */
    void sendUserObjectMessage(Object obj,Destination destination);
}
