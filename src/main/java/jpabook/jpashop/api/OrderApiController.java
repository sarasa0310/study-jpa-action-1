package jpabook.jpashop.api;

import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.orderitem.OrderItem;
import jpabook.jpashop.dto.response.OrderResponse;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderFlatResponse;
import jpabook.jpashop.repository.order.query.OrderItemQueryResponse;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import jpabook.jpashop.repository.order.query.OrderQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders") // 엔티티 직접 노출 문제
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        // Lazy 강제 초기화
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(orderItem -> orderItem.getItem().getName());
        }

        return orders;
    }

    @GetMapping("/api/v2/orders") // 엔티티 노출 문제 개선
    public List<OrderResponse> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        return orders.stream()
                .map(OrderResponse::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * -V3
     * 의도와 다른 결과 발생 -> 중복 데이터(ex.주문 2개인데 4개로)
     * distinct 키워드 활용하여 중복 데이터 문제 해결
     * but,페이징 불가능(메모리에서 페이징 -> 매우 위험!)
     */

    @GetMapping("/api/v3/orders")
    public List<OrderResponse> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();

        return orders.stream()
                .map(OrderResponse::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * -V3.1 가장 추천!
     * 페이징 가능, but N+1 문제 발생 -> application.yml default_batch_fetch_size 설정하여 해결
     * 쿼리 수 : 1 + N + M -> 1 + 1 + 1 로 감소
     */

    @GetMapping("/api/v3.1/orders")
    public List<OrderResponse> ordersV3_page(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        return orders.stream()
                .map(OrderResponse::toResponse)
                .collect(Collectors.toList());
    }

    // v4 ~ v6 DTO 조회 방식

    @GetMapping("/api/v4/orders") // N + 1 문제 발생
    public List<OrderQueryResponse> ordersV4() {
        return orderQueryRepository.findOrderQueryResponses();
    }

    @GetMapping("/api/v5/orders") // N + 1 문제 해결, but 작성 코드가 복잡
    public List<OrderQueryResponse> ordersV5() {
        return orderQueryRepository.findAllByResponseOptimization();
    }

    @GetMapping("/api/v6/orders") // 쿼리 1번
    public List<OrderQueryResponse> ordersV6() {
        List<OrderFlatResponse> flats = orderQueryRepository.findAllByResponseFlat();

        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryResponse(o.getOrderId(), o.getMemberName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryResponse(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryResponse(e.getKey().getOrderId(), e.getKey().getMemberName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }

}
