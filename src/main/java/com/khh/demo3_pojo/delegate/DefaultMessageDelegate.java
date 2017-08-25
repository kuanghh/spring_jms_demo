package com.khh.demo3_pojo.delegate;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/**
 * Created by 951087952@qq.com on 2017/8/25.
 *
 * 默认的消息代理实现类
 */
public class DefaultMessageDelegate implements MessageDelegate{
    @Override
    public void handlerMessage(String message) {
        System.out.println("DefaultMessageDelegate.handlerMessage(String message)处理了该消息");
        System.out.println(message);
    }

    @Override
    public void handlerMessage(byte[] message) {
        System.out.println("DefaultMessageDelegate.handlerMessage(byte[] message)处理了该消息");
        try {
            System.out.println(new String(message,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handlerMessage(Map<String,Object> message) {
        System.out.println("DefaultMessageDelegate.handlerMessage(Map<String,Object> message)处理了该消息");
        for(Map.Entry<String,Object> entry : message.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key :" + key + ",value : " + value);
        }
    }

    @Override
    public void handlerMessage(Serializable message) {
        System.out.println("DefaultMessageDelegate.handlerMessage(Serializable message)处理了该消息");
        System.out.println(message.toString());
    }
}
