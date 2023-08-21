package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.value.Address;
import lombok.Data;

@Data
public class MemberResponse {

    private final Long id;
    private final String name;
    private final Address address;

    public static MemberResponse toResponse(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getAddress());
    }

}
