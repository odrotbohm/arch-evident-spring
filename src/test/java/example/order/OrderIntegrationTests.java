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
import static org.mockito.Mockito.*;

import example.customer.Customer.CustomerIdentifier;
import example.inventory.Inventory;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 * @author Oliver Drotbohm
 */
@ApplicationModuleTest
@RequiredArgsConstructor
class OrderIntegrationTests {

	private final OrderManagement orders;
	private final OrderRepository repository;

	@MockitoBean Inventory inventory;

	@Test
	void bootstrapsOrderModule() {

	}

	@Test
	void persistsAndLoadsOrder() {

		var reference = repository.save(new Order(new CustomerIdentifier(UUID.randomUUID())));
		var result = repository.findById(reference.getId());

		// Equal but not the same
		assertThat(result).hasValue(reference);
		assertThat(result).hasValueSatisfying(it -> assertThat(it).isNotSameAs(reference));
	}

	@Test
	void orderCompletionTriggersInventoryUpdate() {

		var order = new Order(new CustomerIdentifier(UUID.randomUUID()));

		orders.complete(order);

		verify(inventory).updateStock();
	}
}
