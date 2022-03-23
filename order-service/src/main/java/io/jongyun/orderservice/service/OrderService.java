package io.jongyun.orderservice.service;

import io.jongyun.orderservice.domain.Order;
import io.jongyun.orderservice.dto.OrderDto;

import java.util.List;

/**
 * @author jongyunha created on 22. 3. 22.
 */
public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);

    OrderDto getOrderByOrderId(String orderId);

    List<Order> getOrdersByUserId(String userId);
}
