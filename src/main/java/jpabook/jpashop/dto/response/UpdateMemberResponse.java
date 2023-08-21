package jpabook.jpashop.dto.response;

import lombok.Data;

@Data
public class UpdateMemberResponse {

    private final Long id;
    private final String name;

}
