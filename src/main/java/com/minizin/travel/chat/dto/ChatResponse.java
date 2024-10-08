package com.minizin.travel.chat.dto;

import java.util.List;

import com.minizin.travel.chat.entity.Chat;
import com.minizin.travel.chat.entity.ChatType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatResponse {

	private Long chatId;
	private ChatType chatType;
	private List<Long> userIds;

	public ChatResponse(Chat chat) {
		this.chatId = chat.getId();
		this.chatType = chat.getChatType();
		this.userIds = chat.getUserIds();
	}
}
