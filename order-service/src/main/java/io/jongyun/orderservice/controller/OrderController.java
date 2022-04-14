package io.jongyun.orderservice.controller;

import io.jongyun.orderservice.domain.Order;
import io.jongyun.orderservice.dto.OrderDto;
import io.jongyun.orderservice.service.OrderService;
import io.jongyun.orderservice.vo.RequestOrder;
import io.jongyun.orderservice.vo.ResponseOrder;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jongyunha created on 22. 4. 14.
 */
@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final Environment env;
    private final OrderService orderService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s",
            env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(
        @PathVariable(value = "userId") String userId,
        @RequestBody RequestOrder orderDetails
    ) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);

        OrderDto createOrder = orderService.createOrder(orderDto);
        ResponseOrder responseOrder = mapper.map(createOrder, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> createOrder(
        @PathVariable(value = "userId") String userId
    ) {
        List<Order> ordersByUserId = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> result = ordersByUserId.stream().map(order -> {
            return new ModelMapper().map(order, ResponseOrder.class);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
