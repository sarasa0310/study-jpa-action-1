package jpabook.jpashop.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.value.Address;
import jpabook.jpashop.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member") // 연관관계의 주인이 아님을 표시(읽기 전용)
    private List<Order> orders = new ArrayList<>();

}
