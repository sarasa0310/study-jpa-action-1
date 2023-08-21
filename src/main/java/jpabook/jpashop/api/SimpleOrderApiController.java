package jpabook.jpashop.api;

import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.repository.order.simplequery.SimpleOrderQueryRepository;
import jpabook.jpashop.repository.order.simplequery.SimpleOrderQueryResponse;
import jpabook.jpashop.dto.response.SimpleOrderResponse;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */

@RestController
@RequiredArgsConstructor
public class SimpleOrderApiController {

    private final OrderRepository orderRepository;
    private final SimpleOrderQueryRepository simpleOrderQueryRepository;

    @GetMapping("/api/v1/simple-orders") // 엔티티 노출 & 성능 문제
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        for (Order order : orders) { // Lazy 강제 초기화
            order.getMember().getName();
            order.getDelivery().getAddress();
        }

        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderResponse> ordersV2() { // Lazy 로딩 쿼리 성능 문제
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        return orders.stream()
                .map(SimpleOrderResponse::toResponse)
                .collect(Collectors.toList());
    }

    // v3 & v4 우열 X, 각각의 trade-off가 있음

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderResponse> ordersV3() { // fetch 조인 활용하여 성능 개선 -> v3 추천!
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        return orders.stream()
                .map(SimpleOrderResponse::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderQueryResponse> ordersV4() { // 필요한 부분만 SELECT 가능, 하지만 Fit하게 쿼리를 직접 작성했으므로 재사용성 거의 X
        return simpleOrderQueryRepository.findOrderResponses();
    }

}
