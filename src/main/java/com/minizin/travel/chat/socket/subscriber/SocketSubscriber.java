package com.minizin.travel.chat.socket.subscriber;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.minizin.travel.chat.dto.MessageCreateRequest;
import com.minizin.travel.chat.dto.MessageType;
import com.minizin.travel.chat.service.MessageService;
import com.minizin.travel.chat.socket.dto.SocketDirectMessage;
import com.minizin.travel.chat.socket.publisher.SocketPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SocketSubscriber {

    private final MessageService messageService;
    private final SocketPublisher socketPublisher;

    @MessageMapping("/chat/{chatId}")
    public void subscribe(@DestinationVariable("chatId") Long chatId, @Payload SocketDirectMessage message) {
        log.info("content: " + message.getContent());
        var request = MessageCreateRequest.builder()
                .messageId(message.getMessageId())
                .messageType(MessageType.CHAT)
                .content(message.getContent())
                .senderId(message.getUserId())
                .chatId(chatId)
                .build();

        messageService.createMessage(request);

        var directMessage = SocketDirectMessage.builder()
                .messageId(message.getMessageId())
                .userId(message.getUserId())
                .createdAt(message.getCreatedAt())
                .userName(message.getName())
                .name(message.getName())
                .content(message.getContent())
                .build();

        socketPublisher.sendMessage(chatId, directMessage);
    }
}
