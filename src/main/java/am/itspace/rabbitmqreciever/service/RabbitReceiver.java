package am.itspace.rabbitmqreciever.service;

import am.itspace.rabbitmqreciever.model.Product;
import am.itspace.rabbitmqreciever.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitReceiver {

	private final ObjectMapper objectMapper;

	private final ProductRepository productRepository;

	@RabbitListener(queues = "${queue.name}")
	public void receive(String product) throws JsonProcessingException {
		Product value = objectMapper.readValue(product, Product.class);
		log.info("Received product: {}", product);
		saveProduct(value);
	}

	private void saveProduct(Product product) {
		productRepository.save(product);
	}
}
