package com.minizin.travel.chat.dto;

import java.util.UUID;

import com.minizin.travel.chat.entity.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MessageCreateRequest {

	private String messageId;
	private String content;
	private MessageType messageType;
	private Long chatId;
	private Long senderId;

	public Message toEntity() {
		return Message.builder()
			.id(UUID.fromString(messageId))
			.chatId(this.chatId)
			.senderId(this.senderId)
			.messageType(this.messageType)
			.content(this.content)
			.build();
	}
}
