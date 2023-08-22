package jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryResponse> findOrderQueryResponses() {
        List<OrderQueryResponse> result = findOrders(); // 쿼리 1번 -> N개
        result.forEach(oqr -> {
            List<OrderItemQueryResponse> orderItems = findOrderItems(oqr.getOrderId()); // 쿼리 N번
            oqr.setOrderItems(orderItems);
        });
        return result;
    }

    public List<OrderQueryResponse> findAllByResponseOptimization() {
        List<OrderQueryResponse> result = findOrders(); // 루트 1번 조회

        List<Long> orderIds = toOrderIds(result);

        Map<Long, List<OrderItemQueryResponse>> orderItemMap = findOrderItemMap(orderIds); // 컬렉션 1번 조회

        result.forEach(oqr -> oqr.setOrderItems(orderItemMap.get(oqr.getOrderId())));

        return result;
    }

    private Map<Long, List<OrderItemQueryResponse>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryResponse> orderItems = em.createQuery(
                        "select new jpabook.jpashop.dto.response.OrderItemQueryResponse(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryResponse.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryResponse>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryResponse::getOrderId));

        return orderItemMap;
    }

    private List<Long> toOrderIds(List<OrderQueryResponse> result) {
        return result.stream()
                .map(OrderQueryResponse::getOrderId)
                .collect(Collectors.toList());
    }

    private List<OrderItemQueryResponse> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderItemQueryResponse(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryResponse.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryResponse> findOrders() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryResponse(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryResponse.class
        ).getResultList();
    }

    public List<OrderFlatResponse> findAllByResponseFlat() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderFlatResponse(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)"+
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i", OrderFlatResponse.class
        ).getResultList();
    }

}
