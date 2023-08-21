package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.value.Address;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderResponse {

    private final Long orderId;
    private final String memberName;
    private final LocalDateTime orderDate;
    private final OrderStatus orderStatus;
    private final Address address;

    public static SimpleOrderResponse toResponse(Order order) {
        return new SimpleOrderResponse(
                order.getId(),
                order.getMember().getName(), // LAZY 초기화
                order.getOrderDate(),
                order.getStatus(),
                order.getDelivery().getAddress() // LAZY 초기화
        );
    }

}
