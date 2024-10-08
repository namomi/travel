package com.minizin.travel.chat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.minizin.travel.chat.dto.MessageCreateRequest;
import com.minizin.travel.chat.dto.MessageRequestParam;
import com.minizin.travel.chat.entity.Message;
import com.minizin.travel.chat.repository.ChatRepository;
import com.minizin.travel.chat.repository.MessageRepository;
import com.minizin.travel.chat.socket.dto.SocketDirectMessage;
import com.minizin.travel.chat.socket.publisher.SocketPublisher;
import com.minizin.travel.user.domain.entity.UserEntity;
import com.minizin.travel.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final ChatService chatService;
	private final ChatRepository chatRepository;
	private final UserService userService;
	private final SocketPublisher socketPublisher;
	private final MessageRepository messageRepository;

	public Message createAndSendMessage(MessageCreateRequest request) {
		Long chatId = request.getChatId();
		String content = request.getContent();
		Long senderId = request.getSenderId();
		String messageId = request.getMessageId();

		chatService.getChat(chatId);

		Message message = request.toEntity();

		var entity = messageRepository.save(message);

		UserEntity user = userService.findUser(senderId);

		var directMessage = SocketDirectMessage.builder()
			.messageId(messageId)
			.userId(user.getId())
			.createdAt(entity.getCreatedAt())
			.userName(user.getNickname())
			.name(user.getUsername())
			.content(content)
			.build();

		socketPublisher.sendMessage(chatId, directMessage);

		return entity;
	}

	public Message createMessage(MessageCreateRequest request) {
		return messageRepository.save(request.toEntity());
	}

	public Page<Message> getMessages(MessageRequestParam param) {
		chatRepository.findById(param.getChatId());

		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		Pageable pageable = PageRequest.of(param.getPage(), param.getSize(), sort);
		return messageRepository.findAllByChatId(param.getChatId(), pageable);
	}
}
