package com.minizin.travel.chat.service;

import static com.minizin.travel.chat.exception.ChatException.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minizin.travel.chat.dto.ChatCreateRequest;
import com.minizin.travel.chat.entity.Chat;
import com.minizin.travel.chat.repository.ChatRepository;
import com.minizin.travel.global.exception.CustomException;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;

	@Transactional(readOnly = true)
	public Chat getChat(Long chatId) {return findChat(chatId);}

	@Transactional
	public Chat create(ChatCreateRequest request) {
		List<Long> userIds = request.getUserIds();

		String userIdsJson = convertToJson(userIds);

		List<Chat> chats = chatRepository.findAllByJson(userIdsJson);

		if (!Collections.isEmpty(chats)) {
			return chats.get(0);
		}

		return chatRepository.save(request.toEntity());
	}

	@Transactional(readOnly = true)
	public List<Chat> getUsersChat(Long userId) {
		String userIdJson = String.format("[%d]", userId);
		return chatRepository.findAllByJson(userIdJson);
	}

	@Transactional
	public void leaveChat(Long chatId, Long userId) {
		Chat chat = findChat(chatId);
		chat.leave(userId);

		if (chat.alone()) {
			chatRepository.delete(chat);
		} else {
			chatRepository.save(chat);
		}
	}

	private Chat findChat(Long chatId) {
		return chatRepository.findById(chatId)
			.orElseThrow(() -> new CustomException(CHAT_NOT_FOUND));
	}

	private String convertToJson(List<Long> userIds) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(userIds);
		} catch (JsonProcessingException e) {
			throw new CustomException(INTERNAL_SERVER_ERROR);
		}
	}
}
