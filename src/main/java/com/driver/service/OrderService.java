package com.driver.service;

import java.util.List;

import com.driver.shared.dto.OrderDto;
import org.springframework.stereotype.Service;

/**
 * Handle exception cases for all methods which throw Exception
 */
@Service
public interface OrderService {

	OrderDto createOrder(OrderDto order);
	OrderDto getOrderById(String orderId) throws Exception;
	OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception;
	void deleteOrder(String orderId) throws Exception;
	List<OrderDto> getOrders();
}
