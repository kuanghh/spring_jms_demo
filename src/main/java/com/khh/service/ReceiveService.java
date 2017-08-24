package com.khh.service;


import javax.jms.Destination;

/**
 * Created by 951087952@qq.com on 2017/8/24.
 * 接收消息服务接口
 */
public interface ReceiveService {

    public void getMessageObject(Destination destination);

    public void getMessageWithConvert(Destination destination);
}
