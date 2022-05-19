package PubConfirmation;

import java.util.Vector;

import com.rabbitmq.client.AMQP.Confirm.SelectOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderPubConfiguration {

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

			Vector<String> message = new Vector<String>(5);
			message.add("Olá Sender PubConfig");
			message.add("Primeira mensagem");
			message.add("Segunda mensagem");
			message.add("Ultima mensagem");

			for (int i = 0; i < 4; i++) {
				String body = message.get(i);
				channel.basicPublish(NAME_EXCHANGE, "", null, body.getBytes());
				System.out.println("[x] Mensagem Enviada: " + body);

				// wait 5 segundos
				channel.waitForConfirmsOrDie(5_000);
				System.out.println("[v] Mensagem confirmada");
			}
			System.out.println("[o] Ok.");
		}
	}
}
