package com.talkingPotatoes.potatoesProject.blog.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ListResponse<T> {
    private int count;

    private T data;

}
