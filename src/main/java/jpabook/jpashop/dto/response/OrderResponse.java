package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.orderitem.OrderItem;
import jpabook.jpashop.domain.value.Address;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {

    private final Long orderId;
    private final String memberName;
    private final LocalDateTime orderDate;
    private final OrderStatus orderStatus;
    private final Address address;
//    private final List<OrderItem> orderItems; // v1 추가
    private final List<OrderItemResponse> orderItems;

    public static OrderResponse toResponse(Order order) {
//        order.getOrderItems().forEach(orderItem -> orderItem.getItem().getName()); // v1 LAZY 초기화
        return new OrderResponse(
                order.getId(),
                order.getMember().getName(), // LAZY 초기화
                order.getOrderDate(),
                order.getStatus(),
                order.getDelivery().getAddress(), // LAZY 초기화
                order.getOrderItems()
                        .stream()
                        .map(OrderItemResponse::toResponse)
                        .collect(Collectors.toList())
        );
    }

}
