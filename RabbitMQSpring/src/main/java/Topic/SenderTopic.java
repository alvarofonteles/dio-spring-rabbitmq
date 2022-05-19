package Topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderTopic {

	private static String NAME_EXCHANGE = "topicExchenge";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		try (Connection connection = factory.newConnection()) {

			Channel channel = connection.createChannel();
			System.out.println("SenderTopic Channel: " + channel);
			
			// declara a exchange
			channel.exchangeDeclare(NAME_EXCHANGE, "topic");

			String message = "SenderTopicExchange RabbitMQ. Criado com Spring AMQP 2";
			
			String routingKey = "veloz.laranja.coelho";

			channel.basicPublish(NAME_EXCHANGE, routingKey, null, message.getBytes());

			System.out.println("[x] SenderTopic '" + message + "'");
		}
	}
}
