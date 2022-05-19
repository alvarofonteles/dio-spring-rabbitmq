package PubConfirmation;

import com.rabbitmq.client.AMQP.Confirm.SelectOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SecondReceiverPubConfiguration {

	private static String NAME_EXCHANGE = "fanoutExchenge";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setUsername("admin");
		factory.setPassword("pass123");
		factory.setPort(5672);

		try (Connection connection = factory.newConnection()) {

			Channel channel = connection.createChannel();
			SelectOk selectOk = channel.confirmSelect();
			System.out.println(selectOk);

			// declare at exchange (file name)
			channel.exchangeDeclare(NAME_EXCHANGE, "fanout");

			String message = "Olá Sender Second PubConfig Mensagem número -> ";
			int setOfMessage = 5;
			int outOfMessage = 0;

			for (int i = 1; i <= setOfMessage; i++) {
				String body = message + i;
				channel.basicPublish(NAME_EXCHANGE, "", null, body.getBytes());
				System.out.println("[x] Mensagem Second Enviada: " + body);
				outOfMessage++;

				if (outOfMessage == setOfMessage) {
					// wait 5 segundos
					channel.waitForConfirmsOrDie(5_000);
					System.out.println("[v] Mensagem Second confirmada");
					outOfMessage = 0;
				}
			}
			if (outOfMessage != 0) {
				channel.waitForConfirmsOrDie(5_000);
				System.out.println("[v2] Mensagem Second confirmada 2");
			}

			System.out.println("[o] Ok Second.");
		}
	}
}
