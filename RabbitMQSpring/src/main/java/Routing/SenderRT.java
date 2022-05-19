package Routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderRT {

	private static String ROUTING_KEY = "routingKey";
	private static String ROUTING_KEY_SECOND = "secondRoutingKey";
	private static String NAME_EXCHANGE = "directExchenge";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		try (Connection connection = factory.newConnection()) {

			Channel channel = connection.createChannel();
			System.out.println("SenderRoutingKey Channel: " + channel);

			// declara a exchange
			channel.exchangeDeclare(NAME_EXCHANGE, "direct");

			String message = "SenderRoutingKey RabbitMQ. Criado com Spring AMQP 2";
			String secondMessage = "SenderSecondRoutingKey RabbitMQ. Criado com Spring AMQP 2";

			channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY, null, message.getBytes());
			channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY_SECOND, null, secondMessage.getBytes());

			System.out.println("[x] SenderRoutingKey '" + message + "'");
			System.out.println("[x] SenderSecondRoutingKey '" + secondMessage + "'");
		}
	}
}
