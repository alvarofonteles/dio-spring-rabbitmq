package com.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

	private static String NAME_QUEUE = "Olá RabbitMQ?";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		try (Connection connection = factory.newConnection()) {
			// System.out.println("connection.hashCode: " + connection.hashCode());

			Channel channel = connection.createChannel();
			System.out.println("Sender Channel: " + channel);

			/*
			 * Channel channel2 = connection.createChannel();
			 * System.out.println("channel2: " + channel2);
			 */

			channel.queueDeclare(NAME_QUEUE, false, false, false, null);

			String message = "Olá RabbitMQ, este é o meu primeiro programa criado com Spring AMQP";

			channel.basicPublish("", NAME_QUEUE, null, message.getBytes());

			System.out.println("[x] Sent '" + message + "'");
		}
	}
}
