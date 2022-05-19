package PubSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiverPubSub {
	private static String NAME_EXCHANGE = "fanoutExchange";

	public static void main(String[] args0) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();
		System.out.println(channel);

		String nameQueue = channel.queueDeclare().getQueue();

		// declaração da exchange
		channel.exchangeDeclare(NAME_EXCHANGE, "fanout");
		channel.queueBind(nameQueue, NAME_EXCHANGE, "");

		DeliverCallback deliverycallback = (ConsumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("[*] Mensagem Recebida, ReceiverPubSub: '" + message + "'");
		};

		channel.basicConsume(nameQueue, true, deliverycallback, ConsumerTag -> {
		});
	}
}
