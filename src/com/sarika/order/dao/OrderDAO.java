package com.sarika.order.dao;

import java.sql.SQLException;

import com.sarika.order.dto.Order;

public interface OrderDAO {

	int create (Order order) throws SQLException;
	
	Order read(long id) throws SQLException;
	
	int update(Order order) throws SQLException;
	
	int delete(long id) throws SQLException;
}
