package kafka;

import kafka.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {
	@Autowired
	private ProducerService producerService;

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		StreamTokenizer tokenizer = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
		while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			String topic = tokenizer.sval;
			tokenizer.nextToken();
			String data = tokenizer.sval;
			producerService.sendMessage(topic, data);
		}
	}


}
