#这个是在Spring中使用JMS发送和接受消息的Demo ，这里使用ActiveMQ作为消息代理


用的Window7 32操作系统，
首先下载ActiveMQ，官网：http://activemq.apache.org

然后下载后，进入bin/win32,双击activemq.bat,即可启动ActiveMQ服务

**在代码中，使用JmsTemplate（也就是Service中的JmsOperations）接收消息的最大缺点在于receive()和receiveAndConvert()方法都是同步的。
  这意味着接受者必须耐心等待消息的到来，因此这些方法会一直被阻塞，知道有可用的消息（或者直到超时）。同步接收异步发来的消息，是不是感到很怪异？
  此时要用到消息驱动POJO....
**


总结：

	###通信机制分类异步和同步。
		属于同步的技术有：RMI、Hessian、Burlap、HTTP invoker和WebService
		属于异步的技术有：JMS、AMQP。。
	
	###在异步消息中、有两个主要的概念，一个是消息代理、另一个是目的地
		当一个应用发送消息时，会将消息交给一个代理（这个demo中就使用了ActiveMQ作为消息代理），然后通过代理，再把消息发送到目的地
		尽管不同的消息系统会提供不同的消息路由模式，但是有两种通用的目的地：队列（queue）和主题（topic），
		分别对应消息模型的点对点模型和发布/订阅模型
	
		#####点对点消息模型： 消息发送者----->队列------->接受者
				尽管消息队列中的每一条消息只被投递一个接受者，但是并不意味着只能使用一个接受者从队列中获取信息
				事实上。通常可以使用几个接受者来处理队列中的信息。不过，每个接收者都会处理自己所接收到的消息，
				例如银行排队，每个客服是接收者，每个客户是消息，客户排队就是一条队列，
				当有m个客户，n个客服，也只能出现n个客服同时处理n个客户（n < m）
		
												   |--->订阅者
												   |
		#####发布--订阅消息模型：消息发布者------主题------->订阅者
												   |
												   |--->订阅者
												   
				与队列不用的是，消息不再是只投递给一个接受者，而是主题的所有订阅者都可以接受到消息
				
				
	###异步消息的优点：
			1、同步通信意味着等待。异步不需要
			2、面向消息和解耦， 同步通信会将服务器和客户端的接口绑定在一起，当服务端接口改变后，客户端也需要变化，如WebService
			3、位置独立，消息客户端不需要知道消息来自哪里，只需要知道监听哪个队列或者哪个主题
			4、确保投递，当服务端发送消息后，客户端还没来得及接受前，消息会一直保存，当客户端正常运行时，即可接受到消息