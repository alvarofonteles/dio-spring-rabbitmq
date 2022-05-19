package WorkerQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderWorker {

	private static String NAME_QUEUE = "WorkerQueue RabbitMQ.";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		try (Connection connection = factory.newConnection()) {

			Channel channel = connection.createChannel();
			System.out.println("SenderWorker Channel: " + channel);

			channel.queueDeclare(NAME_QUEUE, false, false, false, null);

			String message = "WorkerQueue RabbitMQ. Criado com Spring AMQP 1";

			channel.basicPublish("", NAME_QUEUE, null, message.getBytes());

			System.out.println("[x] SenderWorker '" + message + "'");
		}
	}
}
