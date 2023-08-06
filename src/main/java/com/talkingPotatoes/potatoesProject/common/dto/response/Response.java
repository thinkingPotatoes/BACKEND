package com.talkingPotatoes.potatoesProject.common.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Response<D> {
	private String message;
	private D data;
}
