package com.marzeta.ordering.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.transaction.annotation.Transactional;

public class OrderingService {

	@Transactional
	public Collection<Order> getOrders() {
		// todo
		return Collections.emptyList();
	}

	@Transactional
	public Order getOrderByNumber(String number) {
		// todo
		return null;
	}
}
