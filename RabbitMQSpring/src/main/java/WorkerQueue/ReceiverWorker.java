package WorkerQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiverWorker {

	private static String NAME_QUEUE = "WorkerQueue RabbitMQ";

	private static void doWork(String task) throws InterruptedException {
		for (char ch : task.toCharArray()) {
			if (ch == '.')
				Thread.sleep(6000);
		}
	}

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		System.out.println("ReceiverWorker Channel: " + channel);

		channel.queueDeclare(NAME_QUEUE, false, false, false, null);

		DeliverCallback deliverycallback = (ConsumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("[*] Mensagem Recebida, ReceiverWorker: '" + message + "'");

			try {
				doWork(message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("[x] Done - ReceiverWorker");
			}
		};

		channel.basicConsume(NAME_QUEUE, true, deliverycallback, ConsumerTag -> {
		});
	}
}
