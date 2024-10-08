package com.minizin.travel.chat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MessageRequestParam {

	private Long chatId;
	private int page;
	private int size;
}
