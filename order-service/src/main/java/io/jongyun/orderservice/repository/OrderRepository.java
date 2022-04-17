package io.jongyun.orderservice.repository;

import io.jongyun.orderservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author jongyunha created on 22. 3. 23.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(String orderId);

    List<Order> findByUserId(String userId);

    List<Order> findAllByUserId(String userId);
}
