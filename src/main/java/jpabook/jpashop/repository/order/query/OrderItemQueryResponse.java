package jpabook.jpashop.repository.order.query;

import lombok.Data;

@Data
public class OrderItemQueryResponse {

    private final Long orderId;
    private final String itemName;
    private final int orderPrice;
    private final int count;

}
