package DLX;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiverDlx {
	private static final String CONSUMER_QUEUE = "queueConsumer";

	public static void main(String[] args0) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		DeliverCallback deliverycallback = (ConsumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("[*] Mensagem Recebida, ReceiverDlx: '" + message + "'");
		};

		channel.basicConsume(CONSUMER_QUEUE, true, deliverycallback, ConsumerTag -> {
		});
	}
}
