package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.value.Address;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderFlatResponse {

    private final Long orderId;
    private final String memberName;
    private final LocalDateTime orderDate;
    private final OrderStatus orderStatus;
    private final Address address;

    private final String itemName;
    private final int orderPrice;
    private final int count;

}
