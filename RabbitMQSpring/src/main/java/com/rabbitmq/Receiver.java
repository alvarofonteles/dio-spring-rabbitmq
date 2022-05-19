package com.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Receiver {

	private static String NAME_QUEUE = "Olá RabbitMQ?";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		System.out.println("Receiver Channel: " + channel);

		channel.queueDeclare(NAME_QUEUE, false, false, false, null);

		DeliverCallback deliverycallback = (ConsumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("[*] Menssagem recebida: '" + message + "'");
		};

		channel.basicConsume(NAME_QUEUE, true, deliverycallback, ConsumerTag -> {
		});
	}
}
