package com.khh.demo3_pojo.delegate;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by 951087952@qq.com on 2017/8/25.
 *
 * 消息代理接口
 */
public interface MessageDelegate {

    void handlerMessage(String message);

    void handlerMessage(byte[] message);

    void handlerMessage(Map<String,Object> message);

    void handlerMessage(Serializable message);


}
