/*
 * Copyright 2022-2024 the original author or authors.
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
package example.order;

import static org.assertj.core.api.Assertions.*;

import example.TestApplication;
import example.customer.Customer.CustomerIdentifier;
import example.order.EventPublicationRegistryTests.FailingAsyncTransactionalEventListener;
import example.order.Order.OrderCompleted;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.modulith.events.EventExternalized;
import org.springframework.modulith.events.core.EventPublicationRegistry;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

/**
 * @author Oliver Drotbohm
 */
@ApplicationModuleTest
@RequiredArgsConstructor
@Import({ FailingAsyncTransactionalEventListener.class, TestApplication.class })
@DirtiesContext
@TestPropertySource(properties = "spring.modulith.events.externalization.enabled=true")
class EventPublicationRegistryTests {

	private final OrderManagement orders;
	private final EventPublicationRegistry registry;
	private final FailingAsyncTransactionalEventListener listener;

	@Test
	void leavesPublicationIncompleteForFailingListener(Scenario scenario) throws Exception {

		var order = new Order(new CustomerIdentifier(UUID.randomUUID()));

		scenario.stimulate(() -> orders.complete(order))
				.andWaitForEventOfType(EventExternalized.class)
				.toArriveAndVerify(__ -> {
					assertThat(listener.getEx()).isNotNull();
					assertThat(registry.findIncompletePublications()).hasSize(1);
				});
	}

	static class FailingAsyncTransactionalEventListener {

		@Getter Exception ex;

		@ApplicationModuleListener
		void on(OrderCompleted event) throws Exception {

			var exception = new IllegalStateException();

			try {

				throw exception;

			} finally {
				this.ex = exception;
			}
		}
	}
}
