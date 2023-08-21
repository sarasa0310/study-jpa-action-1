package jpabook.jpashop.dto.response;

import lombok.Data;

@Data
public class MultiResponse<T> {

    private final int count;
    private final T data;

}
