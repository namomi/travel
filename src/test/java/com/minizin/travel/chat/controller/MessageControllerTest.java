package com.minizin.travel.chat.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;

import com.minizin.travel.chat.dto.MessageCreateRequest;
import com.minizin.travel.chat.dto.MessageCreateResponse;
import com.minizin.travel.chat.dto.MessageRequestParam;
import com.minizin.travel.chat.dto.MessageType;
import com.minizin.travel.chat.entity.Message;
import com.minizin.travel.chat.service.MessageService;

class MessageControllerTest {

	@InjectMocks
	private MessageController messageController;

	@Mock
	private MessageService messageService;

	@Mock
	private UserDetails userDetails;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void createMessageTest() {
		// Given
		String messageId = UUID.randomUUID().toString();
		MessageCreateRequest request = MessageCreateRequest.builder()
			.messageId(messageId)
			.content("Hello, World!")
			.messageType(MessageType.CHAT)
			.chatId(1L)
			.senderId(1L)
			.build();

		Message message = request.toEntity();

		when(messageService.createAndSendMessage(request)).thenReturn(message);
		when(userDetails.getUsername()).thenReturn("testUser");

		// When
		MessageCreateResponse response = messageController.create(request, userDetails);

		// Then
		assertNotNull(response);
		assertEquals(messageId, response.getMessageId().toString());
		assertEquals(request.getContent(), response.getContent());
		assertEquals(request.getMessageType(), response.getMessageType());
		verify(messageService, times(1)).createAndSendMessage(request);
	}

	@Test
	public void listMessagesTest() throws Exception {
		// Given
		MessageRequestParam param = new MessageRequestParam(); // 필요한 파라미터를 설정하세요.

		UUID messageId1 = UUID.randomUUID();
		UUID messageId2 = UUID.randomUUID();

		Message message1 = Message.builder()
			.id(messageId1)
			.messageType(MessageType.CHAT)
			.content("Hello World")
			.senderId(1L)
			.chatId(1L)
			.build();

		Message message2 = Message.builder()
			.id(messageId2)
			.messageType(MessageType.CHAT)
			.content("Goodbye World")
			.senderId(2L)
			.chatId(1L)
			.build();

		Page<Message> page = new PageImpl<>(Arrays.asList(message1, message2), PageRequest.of(0, 2), 2);

		// When
		when(messageService.getMessages(any(MessageRequestParam.class))).thenReturn(page);

		// Then
		Page<Message> response = messageController.list(param, null);

		assertEquals(2, response.getContent().size());
		assertEquals(messageId1, response.getContent().get(0).getId());
		assertEquals("Hello World", response.getContent().get(0).getContent());
		assertEquals(messageId2, response.getContent().get(1).getId());
		assertEquals("Goodbye World", response.getContent().get(1).getContent());

	}
}
