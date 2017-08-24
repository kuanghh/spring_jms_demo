package com.khh.service;

import com.khh.entity.User;

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
}
