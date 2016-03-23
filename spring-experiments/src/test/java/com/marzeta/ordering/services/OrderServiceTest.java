package com.marzeta.ordering.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.marzeta.ordering.model.OrderEntity;
import com.marzeta.ordering.model.OrderItemEntity;

@ContextConfiguration(locations = "/META-INF/spring/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTest {

	private static final String ORDER_NUMBER = "1";

	private static final String ITEM_NAME = "foo";

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	@Before
	public void setUp() throws Exception {
		session = sessionFactory.getCurrentSession();
	}

	@Test
	@Transactional
	public void testSaveOrderWithItems() throws Exception {
		OrderEntity order = createAnOrderWithItem();

		session.save(order);
		session.flush();

		assertNotNull(order.getId());
	}

	@Test
	@Transactional
	public void testSaveAndGet() throws Exception {
		OrderEntity order = createAnOrderWithItem();

		session.save(order);
		session.flush();
		session.clear();
		OrderEntity other = (OrderEntity) session.get(OrderEntity.class, order.getId());

		assertEquals(1, other.getItems().size());
		OrderEntity actual = other.getItems().iterator().next().getOrder();
		assertEquals(order.getNumber(), actual.getNumber());
	}

	@Test
	@Transactional
	public void testSaveAndFind() throws Exception {
		OrderEntity order = createAnOrderWithItem();

		session.save(order);
		session.flush();
		session.clear();
		OrderEntity other = (OrderEntity) session
				.createQuery("select o"
						+ " from " + OrderEntity.class.getSimpleName() + " o"
						+ " join o.items i"
						+ " where i.name=:name")
				.setString("name", ITEM_NAME)
				.uniqueResult();

		assertEquals(1, other.getItems().size());
		OrderEntity actual = other.getItems().iterator().next().getOrder();
		assertEquals(order.getNumber(), actual.getNumber());
	}

	private OrderEntity createAnOrderWithItem() {
		OrderEntity order = new OrderEntity();
		order.setNumber(ORDER_NUMBER);
		OrderItemEntity item = new OrderItemEntity();
		item.setName(ITEM_NAME);
		Collection<OrderItemEntity> items = new LinkedHashSet<OrderItemEntity>();
		items.add(item);
		order.setItems(items);
		return order;
	}
}
