package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.orderitem.OrderItem;
import lombok.Data;

@Data
public class OrderItemResponse {

    private final String itemName;
    private final int orderPrice;
    private final int count;

    public static OrderItemResponse toResponse(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getItem().getName(),
                orderItem.getOrderPrice(),
                orderItem.getCount()
        );
    }

}
