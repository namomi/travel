package com.minizin.travel.chat.dto;

import java.util.UUID;

import com.minizin.travel.chat.entity.Message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageCreateResponse {

	private String content;
	private Long chatId;
	private Long senderId;
	private MessageType messageType;
	private UUID messageId;

	public MessageCreateResponse(Message message) {
		this.content = message.getContent();
		this.chatId = message.getChatId();
		this.senderId = message.getSenderId();
		this.messageType = message.getMessageType();
		this.messageId = message.getId();
	}
}
