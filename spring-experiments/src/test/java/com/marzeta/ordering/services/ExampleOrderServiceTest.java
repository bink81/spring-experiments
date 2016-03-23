package com.marzeta.ordering.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.marzeta.ordering.model.ExampleItemEntity;
import com.marzeta.ordering.model.ExampleOrderEntity;

@ContextConfiguration(locations = "/META-INF/spring/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ExampleOrderServiceTest {

	@Autowired
	private SessionFactory sessionFactory;

	@Test
	@Transactional
	public void testSaveOrderWithItems() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		ExampleOrderEntity order = new ExampleOrderEntity();
		order.getItems().add(new ExampleItemEntity());

		session.save(order);
		session.flush();

		assertNotNull(order.getId());
	}

	@Test
	@Transactional
	public void testSaveAndGet() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		ExampleOrderEntity order = new ExampleOrderEntity();
		order.setNumber("1");
		ExampleItemEntity item = new ExampleItemEntity();
		item.setName("foo");
		order.getItems().add(item);

		session.save(order);
		session.flush();
		session.clear();
		ExampleOrderEntity other = (ExampleOrderEntity) session.get(ExampleOrderEntity.class, order.getId());

		assertEquals(1, other.getItems().size());
		ExampleOrderEntity actual = other.getItems().iterator().next().getOrder();
		assertEquals(order.getNumber(), actual.getNumber());
	}

	@Test
	@Transactional
	public void testSaveAndFind() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		ExampleOrderEntity order = new ExampleOrderEntity();
		order.setNumber("1");
		ExampleItemEntity item = new ExampleItemEntity();
		item.setName("foo");
		order.getItems().add(item);

		session.save(order);
		session.flush();
		session.clear();
		ExampleOrderEntity other = (ExampleOrderEntity) session
				.createQuery("select o from ExampleOrderEntity o"
						+ " join o.items i"
						+ " where i.name=:name")
				.setString("name", "foo")
				.uniqueResult();

		assertEquals(1, other.getItems().size());
		ExampleOrderEntity actual = other.getItems().iterator().next().getOrder();
		assertEquals(order.getNumber(), actual.getNumber());
	}
}
