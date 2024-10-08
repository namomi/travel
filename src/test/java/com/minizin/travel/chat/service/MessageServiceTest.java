package com.minizin.travel.chat.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.minizin.travel.chat.dto.MessageCreateRequest;
import com.minizin.travel.chat.dto.MessageRequestParam;
import com.minizin.travel.chat.entity.Chat;
import com.minizin.travel.chat.entity.ChatType;
import com.minizin.travel.chat.entity.Message;
import com.minizin.travel.chat.repository.ChatRepository;
import com.minizin.travel.chat.repository.MessageRepository;
import com.minizin.travel.chat.socket.dto.SocketDirectMessage;
import com.minizin.travel.chat.socket.publisher.SocketPublisher;
import com.minizin.travel.user.domain.entity.UserEntity;
import com.minizin.travel.user.service.UserService;

class MessageServiceTest {

	@InjectMocks
	private MessageService messageService;

	@Mock
	private ChatService chatService;

	@Mock
	private ChatRepository chatRepository;

	@Mock
	private UserService userService;

	@Mock
	private SocketPublisher socketPublisher;

	@Mock
	private MessageRepository messageRepository;

	private MessageCreateRequest request;
	private Message message;
	private UserEntity user;
	private Chat chat;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		user = UserEntity.builder()
			.id(1L)
			.username("testUser")
			.nickname("테스트 사용자")
			.build();

		// 테스트를 위한 메시지 데이터 생성
		message = Message.builder()
			.id(java.util.UUID.randomUUID())
			.content("테스트 메시지")
			.senderId(1L)
			.chatId(1L)
			.build();

		// 메시지 생성 요청 데이터
		request = MessageCreateRequest.builder()
			.messageId(message.getId().toString())
			.content(message.getContent())
			.chatId(message.getChatId())
			.senderId(message.getSenderId())
			.build();

		chat = Chat.builder()
			.id(1L)
			.chatType(ChatType.DIRECT)
			.userIds(List.of(1L, 2L))
			.build();
	}

	@Test
	void createAndSendMessageTest() {
		// Given
		when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));
		when(messageRepository.save(any(Message.class))).thenReturn(message);
		when(userService.findUser(1L)).thenReturn(user);

		// When
		Message result = messageService.createAndSendMessage(request);

		// Then
		assertNotNull(result);
		assertEquals(message.getContent(), result.getContent());

		verify(socketPublisher, times(1)).sendMessage(any(Long.class), any(SocketDirectMessage.class));
		verify(messageRepository, times(1)).save(any(Message.class));
	}

	@Test
	void createMessageTest() {
		// Given
		when(messageRepository.save(any(Message.class))).thenReturn(message);

		// When
		Message result = messageService.createMessage(request);

		// Then
		assertNotNull(result);
		assertEquals(message.getContent(), result.getContent());
		verify(messageRepository, times(1)).save(any(Message.class));
	}

	@Test
	void getMessagesTest() {
		// Given
		MessageRequestParam param = new MessageRequestParam();
		param.setChatId(1L);
		param.setPage(0);
		param.setSize(10);

		Page<Message> messagePage = new PageImpl<>(List.of(message), PageRequest.of(0, 10), 1);
		when(chatRepository.findById(param.getChatId())).thenReturn(Optional.of(chat));
		when(messageRepository.findAllByChatId(param.getChatId(),
			PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt")))).thenReturn(messagePage);

		// When
		Page<Message> result = messageService.getMessages(param);

		// Then
		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals(message.getContent(), result.getContent().get(0).getContent());
		verify(chatRepository, times(1)).findById(param.getChatId());
		verify(messageRepository, times(1)).findAllByChatId(param.getChatId(),
			PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
	}
}
