package com.sarika.order.bo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.sql.SQLException;

import com.sarika.order.bo.exception.BOException;
import com.sarika.order.dao.OrderDAO;
import com.sarika.order.dto.Order;

public class OrderBOImplTest {

	private static final int ORDER_ID = 123;
	@Mock
	OrderDAO dao;
	private OrderBOImpl bo;
	private Order Order;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		bo = new OrderBOImpl();
		bo.setDao(dao);
	}

	@Test
	public void placeOrder_Should_Create_An_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.create(any(Order.class))).thenReturn(new Integer(1));

		boolean result = bo.placeOrder(order);

		assertTrue(result);
		verify(dao,atLeast(1)).create(order);  //times
	}

	@Test
	public void placeOrder_Should_not_Create_An_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.create(order)).thenReturn(new Integer(0));

		boolean result = bo.placeOrder(order);

		assertFalse(result);
		verify(dao).create(order);
	}

	@Test(expected = BOException.class)
	public void placeOrder_Should_Throw_BOException() throws BOException, SQLException {

		Order order = new Order();
		when(dao.create(order)).thenThrow(SQLException.class);
		boolean result = bo.placeOrder(order);
	}

	@Test
	public void cancelOrder_Should_Cancel_The_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(ORDER_ID)).thenReturn(order);
		when(dao.update(order)).thenReturn(1);
		boolean result = bo.cancelOrder(ORDER_ID);

		assertTrue(result);

		verify(dao).read(ORDER_ID);
		verify(dao).update(order);
	}

	@Test
	public void cancelOrder_Should_Not_Cancel_The_Order() throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(ORDER_ID)).thenReturn(order);
		when(dao.update(order)).thenReturn(0);
		boolean result = bo.cancelOrder(ORDER_ID);

		assertFalse(result);

		verify(dao).read(ORDER_ID);
		verify(dao).update(order);

	}

	@Test(expected = BOException.class)
	public void cancelOrder_ShouldThrowABOExceptionOnRead() throws SQLException, BOException {
		when(dao.read(anyInt())).thenThrow(SQLException.class);
		bo.cancelOrder(ORDER_ID);
	}

	@Test(expected = BOException.class)
	public void cancelOrder_Should_Throw_A_BOException_OnUpdate() throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(ORDER_ID)).thenReturn(order);
		when(dao.update(order)).thenThrow(SQLException.class);
		bo.cancelOrder(ORDER_ID);
	}

	@Test
	public void deleteOrder_Deletes_The_Order() throws SQLException, BOException {
		when(dao.delete(ORDER_ID)).thenReturn(1);
		boolean result = bo.deleteOrder(ORDER_ID);
		assertTrue(result);
		verify(dao).delete(ORDER_ID);
	}
	
	@Test
	public void deleteOrder_Should_Not_Delete_The_Order() throws SQLException, BOException {
		when(dao.delete(ORDER_ID)).thenReturn(0);
		 boolean result = bo.deleteOrder(ORDER_ID);
		 assertFalse(result);
		 verify(dao).delete(ORDER_ID);
	}
	
	@Test(expected=BOException.class)
	public void deleteOrder_Should_Throw_A_BOException() throws BOException, SQLException {
		when(dao.delete(ORDER_ID)).thenThrow(SQLException.class);
		bo.deleteOrder(ORDER_ID);
	}
	
}
