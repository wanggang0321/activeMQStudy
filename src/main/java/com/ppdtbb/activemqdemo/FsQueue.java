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
	
	//queue����
	public static void testMQProducerQueue() throws JMSException {
		
		//1.�����������Ӷ�����Ҫָ��ip�Ͷ˿ں�
		ConnectionFactory f = new ActiveMQConnectionFactory("tcp://192.168.42.51:61616");
		//2.ʹ�����ӹ�������һ�����Ӷ���
		Connection c = f.createConnection();
		//3.��������
		c.start();
		//4.ʹ�����Ӷ��󴴽��Ự����
		Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.ʹ�ûỰ���󴴽�Ŀ����󣬰���queue��topic��һ��һ��һ�Զࣩ
		Queue q = s.createQueue("test-queue");
		//6.ʹ�ûỰ���󴴽������߶���
		MessageProducer p = s.createProducer(q);
		//7.ʹ�ûỰ���󴴽�һ����Ϣ����
		TextMessage t = s.createTextMessage("Hello!xinglongjiayuan0725");
		//8.������Ϣ
		p.send(t);
		//9.�ر���Դ
		p.close();
		s.close();
		c.close();
	}
	
	//queue����
	public static void testMQConsumerQueue() throws JMSException, IOException {
		//1.�����������Ӷ�����Ҫָ��ip�Ͷ˿ں�
		ConnectionFactory f = new ActiveMQConnectionFactory("tcp://192.168.42.51:61616");
		//2.ʹ�����ӹ�������һ�����Ӷ���
		Connection c = f.createConnection();
		//3.��ʼ����
		c.start();
		//4.ʹ�����Ӷ��󴴽��Ự����
		Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.ʹ�ûỰ���󴴽�Ŀ����󣬰���queue��topic��һ��һ��һ�Զࣩ
		Queue q = s.createQueue("test-queue");
		//6.ʹ�ûỰ���󴴽������߶���
		MessageConsumer co = s.createConsumer(q);
		//7.��consumer����������һ��messageListener��������������Ϣ
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
		//8.����ȴ������û���Ϣ
		System.in.read();
		//9.�ر���Դ
		co.close();
		s.close();
		c.close();
	}
	
}
