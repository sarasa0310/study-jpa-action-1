package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.value.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "orderId")
public class OrderQueryResponse {

    private Long orderId;
    private String memberName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryResponse> orderItems; // 추가

    public OrderQueryResponse(Long orderId, String memberName, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public OrderQueryResponse(Long orderId, String memberName, LocalDateTime orderDate, OrderStatus orderStatus, Address address, List<OrderItemQueryResponse> orderItems) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }

}
