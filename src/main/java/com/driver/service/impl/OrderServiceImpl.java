package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    int generatedId = 1;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto order) {
        OrderEntity orderEntity = OrderEntity.builder().
                cost(order.getCost()).
                items(order.getItems()).
                userId(order.getUserId()).
                status(false).
                orderId(Integer.toString(generatedId)).
                build();

        generatedId++;
        orderRepository.save(orderEntity);
        orderEntity = orderRepository.findByOrderId(orderEntity.getOrderId());
        order.setOrderId(orderEntity.getOrderId());
        order.setId(orderEntity.getId());
        return order;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if(orderEntity == null){
            throw new Exception("Order not found");
        }
        OrderDto orderDto = convertEntityToDto(orderEntity);
        return orderDto;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        OrderEntity orderEntity = OrderEntity.builder().
                cost(order.getCost()).
                items(order.getItems()).
                userId(order.getUserId()).
                status(false).
                orderId(orderId).
                id(orderRepository.findByOrderId(orderId).getId()).
                build();

        orderRepository.save(orderEntity);
        OrderDto orderDto = convertEntityToDto(orderEntity);

        return orderDto;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        orderRepository.delete(orderEntity);
    }

    @Override
    public List<OrderDto> getOrders() {
        Iterable<OrderEntity> orderEntityList = orderRepository.findAll();
        List <OrderDto> orderDtoList = new ArrayList<>();

        for(OrderEntity orderEntity: orderEntityList){
            OrderDto orderDto = convertEntityToDto(orderEntity);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

    public OrderDto convertEntityToDto(OrderEntity orderEntity){
        OrderDto orderDto = OrderDto.builder().
                id(orderEntity.getId()).
                status(orderEntity.isStatus()).
                userId(orderEntity.getUserId()).
                cost(orderEntity.getCost()).
                items(orderEntity.getItems()).build();

        return orderDto;
    }
}