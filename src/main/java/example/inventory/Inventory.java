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
package example.inventory;

import example.order.Order.OrderCompleted;
import lombok.RequiredArgsConstructor;

import org.jmolecules.ddd.annotation.Service;
import org.springframework.modulith.events.ApplicationModuleListener;

/**
 * @author Oliver Drotbohm
 */
@Service
@RequiredArgsConstructor
public class Inventory {

	@SuppressWarnings("unused") //
	private final InventoryRepository repository;

	/**
	 * Updates the stock for all line items contained in the order.
	 */
	@ApplicationModuleListener
	void updateStock(OrderCompleted order) {

	}
}
