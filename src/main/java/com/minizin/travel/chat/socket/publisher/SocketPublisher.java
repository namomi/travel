package com.minizin.travel.chat.socket.publisher;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import com.minizin.travel.chat.socket.dto.SocketDirectMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SocketPublisher {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Async
    public void sendMessage(Long chatId, SocketDirectMessage message) {
        log.info("send message. chatId: "+ chatId + " content: "+ message.getContent());
        simpMessagingTemplate.convertAndSend("/topic/directChat/" + chatId, message);
    }
}
