package com.sarika.order.bo;

import com.sarika.order.bo.exception.BOException;
import com.sarika.order.dto.Order;

public interface OrderBO {

	boolean placeOrder(Order order) throws BOException;
	
	boolean cancelOrder(long id) throws BOException;
	
	boolean deleteOrder(long id) throws BOException;
}
