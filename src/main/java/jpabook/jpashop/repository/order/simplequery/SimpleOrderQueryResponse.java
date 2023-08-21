package jpabook.jpashop.repository.order.simplequery;

import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.value.Address;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderQueryResponse {

    private final Long orderId;
    private final String memberName;
    private final LocalDateTime orderDate;
    private final OrderStatus orderStatus;
    private final Address address;

}
