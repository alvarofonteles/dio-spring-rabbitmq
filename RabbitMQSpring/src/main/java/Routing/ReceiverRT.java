package Routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiverRT {

	private static String ROUTING_KEY = "routingKey";
	private static String NAME_EXCHANGE = "directExchenge";

	public static void main(String[] args0) throws Exception {
		// primeiro criar a conexão
		// setar as informações para cria-la
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		Connection connection = factory.newConnection();

		// criar um novo canal
		Channel channel = connection.createChannel();
		System.out.println("ReceiverRoutingKey Channel: " + channel);

		// o servidor irá determinar um nome randomico para esta fila
		// consequentemente ela será temporária
		String nameQueue = channel.queueDeclare().getQueue();

		// declaração da exchange
		channel.exchangeDeclare(NAME_EXCHANGE, "direct");
		channel.queueBind(nameQueue, NAME_EXCHANGE, ROUTING_KEY);

		DeliverCallback deliverycallback = (ConsumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("[*] Mensagem Recebida, ReceiverRoutingKey: '" + message + "'");
		};

		channel.basicConsume(nameQueue, true, deliverycallback, ConsumerTag -> {
		});
	}
}
