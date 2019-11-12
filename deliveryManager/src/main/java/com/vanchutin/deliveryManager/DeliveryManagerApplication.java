package com.vanchutin.deliveryManager;

import com.vanchutin.deliveryManager.dao.DeliveryDao;
import com.vanchutin.deliveryManager.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(com.vanchutin.annotation.ResourceSqlAnnotationBeanPostProcessor.class)
public class DeliveryManagerApplication{

	public static void main(String[] args) {
		SpringApplication.run(DeliveryManagerApplication.class, args);
	}

}
