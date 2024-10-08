package com.minizin.travel.chat.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.minizin.travel.chat.dto.ChatCreateRequest;
import com.minizin.travel.chat.dto.ChatResponse;
import com.minizin.travel.chat.entity.Chat;
import com.minizin.travel.chat.service.ChatService;
import com.minizin.travel.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

	private final UserService userService;
	private final ChatService chatService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ChatResponse create(@RequestBody ChatCreateRequest request, @AuthenticationPrincipal UserDetails user) {
		Chat chat = chatService.create(request);
		return new ChatResponse(chat);
	}

	@GetMapping("/{id}")
	public ChatResponse detail(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
		Chat chat = chatService.getChat(id);
		return new ChatResponse(chat);
	}

	@GetMapping
	public List<ChatResponse> list(@AuthenticationPrincipal UserDetails user) {
		Long userId = userService.getUserByUsername(user.getUsername()).getId();
		List<Chat> chats = chatService.getUsersChat(userId);
		return chats.stream()
			.map(ChatResponse::new)
			.collect(Collectors.toList());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void leave(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
		Long userId = userService.getUserByUsername(user.getUsername()).getId();
		chatService.leaveChat(id, userId);
	}
}
