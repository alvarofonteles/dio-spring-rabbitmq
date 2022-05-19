package Topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class SecondReceiverTopic {

	private static String NAME_EXCHANGE = "topicExchenge";

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
		System.out.println("SecondReceiverTopic Channel: " + channel);

		// o servidor irá determinar um nome randomico para esta fila
		// consequentemente ela será temporária
		String nameQueue = channel.queueDeclare().getQueue();
		
		// associado ao routingKey do Sender
		String bindingKey = "veloz.#";
		//String bindingKey = "#.coelho";

		// declaração da exchange
		channel.exchangeDeclare(NAME_EXCHANGE, "topic");
		channel.queueBind(nameQueue, NAME_EXCHANGE, bindingKey);

		DeliverCallback deliverycallback = (ConsumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("[*] Mensagem Recebida, SecondReceiverTopic: '" + message + "'");
		};

		channel.basicConsume(nameQueue, true, deliverycallback, ConsumerTag -> {
		});
	}
}
