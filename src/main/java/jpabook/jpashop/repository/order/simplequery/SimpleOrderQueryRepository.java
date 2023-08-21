package jpabook.jpashop.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SimpleOrderQueryRepository { // 가장 최적화된 쿼리용 레포지토리

    private final EntityManager em;

    public List<SimpleOrderQueryResponse> findOrderResponses() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.simplequery.SimpleOrderQueryResponse(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", SimpleOrderQueryResponse.class
        ).getResultList();
    }

}
