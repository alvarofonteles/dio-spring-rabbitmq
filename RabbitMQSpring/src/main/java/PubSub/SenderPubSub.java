package PubSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderPubSub {

	private static String NAME_EXCHAGE = "fanoutExchange";

	public static void main(String[] args0) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		try (Connection connection = factory.newConnection()) {

			Channel channel = connection.createChannel();
			System.out.println(channel);

			channel.exchangeDeclare(NAME_EXCHAGE, "fanout");

			String message = "SenderPubSub RabbitMQ. Criado com Spring AMQP 2";

			channel.basicPublish(NAME_EXCHAGE, "", null, message.getBytes());

			System.out.print("[x] SenderPubSub '" + message + "'");
		}
	}
}
