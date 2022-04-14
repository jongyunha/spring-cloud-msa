package io.jongyun.orderservice.service;

import io.jongyun.orderservice.domain.Order;
import io.jongyun.orderservice.dto.OrderDto;
import io.jongyun.orderservice.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jongyunha created on 22. 3. 23.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDetails) {
        orderDetails.setOrderId(UUID.randomUUID().toString());
        orderDetails.setTotalPrice(orderDetails.getUnitPrice() * orderDetails.getQty());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Order order = mapper.map(orderDetails, Order.class);

        orderRepository.save(order);

        OrderDto orderDto = mapper.map(order, OrderDto.class);
        return orderDto;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId).orElseThrow(RuntimeException::new);
        return new ModelMapper().map(order, OrderDto.class);
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
