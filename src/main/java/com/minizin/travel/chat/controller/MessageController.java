package com.minizin.travel.chat.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minizin.travel.chat.dto.MessageCreateRequest;
import com.minizin.travel.chat.dto.MessageCreateResponse;
import com.minizin.travel.chat.dto.MessageRequestParam;
import com.minizin.travel.chat.entity.Message;
import com.minizin.travel.chat.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {

	private final MessageService messageService;

	@PostMapping
	public MessageCreateResponse create(@RequestBody MessageCreateRequest request,
		@AuthenticationPrincipal UserDetails user) {
		var message = messageService.createAndSendMessage(request);
		return new MessageCreateResponse(message);
	}

	@GetMapping
	public Page<Message> list(MessageRequestParam param, @AuthenticationPrincipal UserDetails user) {
		return messageService.getMessages(param);
	}
}
