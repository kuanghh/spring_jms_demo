package com.khh.demo4_anno.conf;

import com.khh.demo2_pojo.listener.ExampleListener;
import com.khh.demo4_anno.validator.UserValidator;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.jms.support.destination.JmsDestinationAccessor;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import javax.jms.Destination;


/**
 * Created by 951087952@qq.com on 2017/8/25.
 *
 *  当实现了JmsListenerConfigurer该接口之后，才能使用configureJmsListeners(JmsListenerEndpointRegistrar jmsListenerEndpointRegistrar)
 *  目的是为了配置多个独立的监听器，监听指定的队列或者主题,而不用@JmsListener注解。
 *
 */
@Configuration
@EnableJms//开启对JMS的支持
public class SpringJMSConf implements JmsListenerConfigurer {

    /**
     * 注意这里是 DefaultJmsListenerContainerFactory，而不是 DefaultMessageListenerContainer
     * @return
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setDestinationResolver(destinationResolver());
        factory.setConcurrency("3-10");//设置这个之后，可能MyService.processOrder方法会被重复3-10次，因为开启了3-10个线程来监听
        return factory;
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://localhost:61616");
        factory.setTrustAllPackages(true);
        return factory;
    }

    @Bean
    public DestinationResolver destinationResolver() {
        DynamicDestinationResolver resolver = new DynamicDestinationResolver();
        return resolver;
    }

    @Bean
    public Destination queue(){
        return new ActiveMQQueue("user.queue");
    }

    @Bean
    public Destination queue2(){
        return new ActiveMQQueue("user.queue.new");
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setDefaultDestination(queue());
        jmsTemplate.setMessageConverter(messageConverter());
        return jmsTemplate;
    }

    @Bean
    public MessageConverter messageConverter(){
        return new MappingJackson2MessageConverter();
    }

    /**
     * 配置这个，与demo2的例子差不多,也就是配置独立的监听器，监听指定的队列或者主题
     *
     *  注意：因为监听的是队列，每个消息只能被一个接受者获取，所以，如果要使用该方法，要确保监听者唯一，
     *  所以要吧Myservice的@Componet注解去掉，
     *  不然的话，会出现两个监听者，同时监听一条队列（这样做并不是不可以，只是会出现有的时候MyService收到消息，有的时候ExamplerListener收到消息）
     * @param jmsListenerEndpointRegistrar
     */
//    @Override
//    public void configureJmsListeners(JmsListenerEndpointRegistrar jmsListenerEndpointRegistrar) {
//        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
//        endpoint.setId("myJmsEndpoint");
//        endpoint.setDestination("user.queue");
//        endpoint.setMessageListener(new ExampleListener());
//        jmsListenerEndpointRegistrar.registerEndpoint(endpoint);
//    }

    /**
     * 如果要 校验接收到的信息，  可以采取如下措施
     * @param jmsListenerEndpointRegistrar
     */
    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar jmsListenerEndpointRegistrar) {
        jmsListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    /**
     *注册校验器
     * @return
     */
    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory(){
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setValidator(myValidator());
        return factory;
    }

    /**
     * 创建一个User的校验器
     * @return
     */
    @Bean
    public UserValidator myValidator(){
        return new UserValidator();
    }

}
