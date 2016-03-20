package com.marzeta.ordering.model;

import java.util.Collection;
import java.util.LinkedHashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_ORDER")
public class ExampleOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String customer;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORDER_ID")
	private Collection<ExampleItem> items = new LinkedHashSet<ExampleItem>();

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Collection<ExampleItem> getItems() {
		return items;
	}

	public void setItems(Collection<ExampleItem> items) {
		this.items = items;
	}

	public Long getId() {
		return id;
	}
}
