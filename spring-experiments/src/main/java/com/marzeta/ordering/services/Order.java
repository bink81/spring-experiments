package com.marzeta.ordering.services;

import java.util.Collection;

public class Order {
	private final String number;

	private final Collection<OrderItem> items;

	public Order(String number, Collection<OrderItem> items) {
		super();
		this.number = number;
		this.items = items;
	}

	public final String getNumber() {
		return number;
	}

	public final Collection<OrderItem> getItems() {
		return items;
	}

}
