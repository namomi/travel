package com.minizin.travel.chat.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import com.minizin.travel.chat.dto.ChatCreateRequest;
import com.minizin.travel.chat.dto.ChatResponse;
import com.minizin.travel.chat.entity.Chat;
import com.minizin.travel.chat.entity.ChatType;
import com.minizin.travel.chat.service.ChatService;
import com.minizin.travel.user.domain.entity.UserEntity;
import com.minizin.travel.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

	@InjectMocks
	private ChatController chatController;

	@Mock
	private ChatService chatService;

	@Mock
	private UserService userService;

	@Mock
	private UserDetails userDetails;

	@Mock
	private UserDetails user;

	@BeforeEach
	public void setup() {
	}

	@Test
	public void createChatTest() {
		// Given
		ChatCreateRequest request = new ChatCreateRequest(Arrays.asList(1L, 2L));

		Chat chat = Chat.builder()
			.id(1L)
			.userIds(request.getUserIds())
			.chatType(ChatType.DIRECT)
			.build();

		when(chatService.create(any(ChatCreateRequest.class))).thenReturn(chat);

		// When
		ChatResponse response = chatController.create(request, user);

		// Then
		assertEquals(1L, response.getChatId());
		assertEquals(Arrays.asList(1L, 2L), response.getUserIds());
		assertEquals(ChatType.DIRECT, response.getChatType());
	}

	@Test
	public void detail_test() {
		// Given
		Long chatId = 1L;
		Chat chat = Chat.builder()
			.id(chatId)
			.userIds(Arrays.asList(1L, 2L))
			.chatType(ChatType.DIRECT)
			.build();

		when(chatService.getChat(chatId)).thenReturn(chat);

		// When
		ChatResponse response = chatController.detail(chatId, user);

		// Then
		assertEquals(chatId, response.getChatId());
		assertEquals(Arrays.asList(1L, 2L), response.getUserIds());
		assertEquals(ChatType.DIRECT, response.getChatType());
	}

	@Test
	@WithMockUser(username = "testUser")
	public void list_test() {
		// Given
		UserEntity user = UserEntity.builder()
			.id(1L)
			.username("testUser")
			.password("password")
			.build();

		Chat chat1 = Chat.builder()
			.id(1L)
			.userIds(Arrays.asList(1L, 2L))
			.chatType(ChatType.DIRECT)
			.build();

		Chat chat2 = Chat.builder()
			.id(2L)
			.userIds(Arrays.asList(3L, 4L))
			.chatType(ChatType.PUBLIC)
			.build();


		when(userService.getUserByUsername("testUser")).thenReturn(user);
		when(chatService.getUsersChat(user.getId())).thenReturn(Arrays.asList(chat1, chat2));

		when(userDetails.getUsername()).thenReturn("testUser");

		// When
		List<ChatResponse> responses = chatController.list(userDetails);

		// Then
		assertEquals(2, responses.size());
		assertEquals(1L, responses.get(0).getChatId());
		assertEquals(Arrays.asList(1L, 2L), responses.get(0).getUserIds());
		assertEquals(ChatType.DIRECT, responses.get(0).getChatType());

		assertEquals(2L, responses.get(1).getChatId());
		assertEquals(Arrays.asList(3L, 4L), responses.get(1).getUserIds());
		assertEquals(ChatType.PUBLIC, responses.get(1).getChatType());
	}

	@Test
	public void leaveChatTest() {
		// Given
		Long chatId = 1L;
		Long userId = 2L;

		when(userDetails.getUsername()).thenReturn("testUser");

		UserEntity user = UserEntity.builder()
			.id(userId)
			.username("testUser")
			.password("password")
			.build();
		when(userService.getUserByUsername("testUser")).thenReturn(user);

		// When
		chatController.leave(chatId, userDetails);

		// Then
		verify(userService, times(1)).getUserByUsername("testUser");
		verify(chatService, times(1)).leaveChat(chatId, userId);
	}
}


