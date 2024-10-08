package com.minizin.travel.chat.dto;

import static com.minizin.travel.chat.entity.ChatType.*;

import java.util.List;

import com.minizin.travel.chat.entity.Chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatCreateRequest {

	private List<Long> userIds;

	public Chat toEntity() {
		return Chat.builder()
			.userIds(userIds)
			.chatType(DIRECT)
			.build();
	}
}
