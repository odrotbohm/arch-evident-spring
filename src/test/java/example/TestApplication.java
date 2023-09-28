/*
 * Copyright 2023-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import example.customer.Customer.CustomerIdentifier;
import example.order.Order;
import example.order.OrderManagement;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * @author Oliver Drotbohm
 */
@TestConfiguration
public class TestApplication {

	@Bean
	@ServiceConnection
	KafkaContainer kafkaContainer() {
		return new KafkaContainer(DockerImageName.parse("apache/kafka-native:latest"));
	}

	public static void main(String[] args) {

		var ctx = SpringApplication.from(Application::main)
				.with(TestApplication.class)
				.run(args)
				.getApplicationContext();

		var orders = ctx.getBean(OrderManagement.class);

		orders.complete(new Order(new CustomerIdentifier(UUID.randomUUID())));
	}
}
