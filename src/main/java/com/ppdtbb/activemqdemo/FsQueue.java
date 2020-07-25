package com.ppdtbb.activemqdemo;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class FsQueue {
	
	public static void main(String[] args) {
		try {
			//testMQProducerQueue();
			testMQConsumerQueue();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//queue发送
	public static void testMQProducerQueue() throws JMSException {
		
		//1.创建工厂连接对象，需要指定ip和端口号
		ConnectionFactory f = new ActiveMQConnectionFactory("tcp://192.168.42.51:61616");
		//2.使用连接工厂创建一个连接对象
		Connection c = f.createConnection();
		//3.开启连接
		c.start();
		//4.使用连接对象创建会话对象
		Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
		Queue q = s.createQueue("test-queue");
		//6.使用会话对象创建生产者对象
		MessageProducer p = s.createProducer(q);
		//7.使用会话对象创建一个消息对象
		TextMessage t = s.createTextMessage("Hello!xinglongjiayuan0725");
		//8.发送消息
		p.send(t);
		//9.关闭资源
		p.close();
		s.close();
		c.close();
	}
	
	//queue接收
	public static void testMQConsumerQueue() throws JMSException, IOException {
		//1.创建工厂连接对象，需要指定ip和端口号
		ConnectionFactory f = new ActiveMQConnectionFactory("tcp://192.168.42.51:61616");
		//2.使用连接工厂创建一个连接对象
		Connection c = f.createConnection();
		//3.开始连接
		c.start();
		//4.使用连接对象创建会话对象
		Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
		Queue q = s.createQueue("test-queue");
		//6.使用会话对象创建消费者对象
		MessageConsumer co = s.createConsumer(q);
		//7.向consumer对象中设置一个messageListener对象，用来接收消息
		co.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				if(message instanceof TextMessage) {
					TextMessage t = (TextMessage) message;
					try {
						System.out.println(t.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		//8.程序等待接收用户消息
		System.in.read();
		//9.关闭资源
		co.close();
		s.close();
		c.close();
	}
	
}
