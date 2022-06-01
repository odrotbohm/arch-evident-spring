/*
 * Copyright 2022 the original author or authors.
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

import example.customer.CustomerId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Oliver Drotbohm
 */
@Entity
@Getter
@Table(name = "MyOrder")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true)
public class Order {

	private final @EmbeddedId OrderIdentifier id;
	private final CustomerId customerId;
	private Status status;

	@OneToMany(cascade = CascadeType.ALL) //
	private final List<LineItem> lineItems;

	public Order(CustomerId customerId) {

		this.id = OrderIdentifier.of(UUID.randomUUID());
		this.status = Status.OPEN;
		this.customerId = customerId;
		this.lineItems = new ArrayList<>();
	}

	Order complete() {

		this.status = Status.COMPLETED;

		return this;
	}

	Order add(LineItem item) {

		this.lineItems.add(item);

		return this;
	}

	@Embeddable
	@EqualsAndHashCode
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(force = true)
	public static class OrderIdentifier implements Serializable {

		private static final long serialVersionUID = 1009997590119941755L;
		private final UUID orderId;
	}

	enum Status {
		OPEN, COMPLETED, CANCELLED;
	}

	@Entity
	@Getter
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
	static class LineItem {

		private @EmbeddedId LineItemId id;
		private String description;
		private long amount;

		LineItem(String description, long amount) {

			this.id = LineItemId.of(UUID.randomUUID().toString());
			this.description = description;
			this.amount = amount;
		}

		@Embeddable // remove
		@EqualsAndHashCode
		@RequiredArgsConstructor(staticName = "of")
		@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // remove
		static class LineItemId implements Serializable {

			private static final long serialVersionUID = 1009997590119941755L;
			private final String lineItemId;
		}
	}
}
