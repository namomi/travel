package com.minizin.travel.chat.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.minizin.travel.chat.dto.ChatCreateRequest;
import com.minizin.travel.chat.entity.Chat;
import com.minizin.travel.chat.entity.ChatType;
import com.minizin.travel.chat.repository.ChatRepository;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {


	@Mock
	private ChatRepository chatRepository;

	@InjectMocks
	private ChatService chatService;

	private ChatCreateRequest request;
	private Chat chat;

	@BeforeEach
	public void setUp() {
		request = new ChatCreateRequest(Arrays.asList(1L, 2L));
		chat = Chat.builder()
			.id(1L)
			.chatType(ChatType.DIRECT)
			.userIds(new ArrayList<>(request.getUserIds()))
			.build();
	}

	@Test
	public void getChatTest() {
		// Given
		Long chatId = 1L;
		when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

		// When
		Chat result = chatService.getChat(chatId);

		// Then
		assertNotNull(result);
		assertEquals(chatId, result.getId());
	}

	@Test
	public void createChatTest() {
		// Given
		when(chatRepository.findAllByJson(anyString())).thenReturn(Arrays.asList());
		when(chatRepository.save(any(Chat.class))).thenReturn(chat);

		// When
		Chat result = chatService.create(request);

		// Then
		assertNotNull(result);
		assertEquals(1L, result.getId());
	}

	@Test
	public void getUsersChatTest() {
		// Given
		Long userId = 1L;
		List<Chat> chats = Arrays.asList(chat);
		when(chatRepository.findAllByJson(anyString())).thenReturn(chats);

		// When
		List<Chat> result = chatService.getUsersChat(userId);

		// Then
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(chat.getId(), result.get(0).getId());
	}

	@Test
	public void leaveChatTest() {
		// Given
		Long chatId = 1L;
		Long userId = 1L;

		when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

		// When
		chatService.leaveChat(chatId, userId);

		// Then
		verify(chatRepository, never()).save(chat);
		verify(chatRepository, times(1)).delete(chat);
	}
}
