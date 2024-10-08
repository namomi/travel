package com.minizin.travel.chat.entity;

import java.util.UUID;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.minizin.travel.chat.dto.MessageType;
import com.minizin.travel.global.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

	@Id
	private UUID id;

	private MessageType messageType;

	private String content;

	private Long senderId;

	private Long chatId;

	private UUID generateId() {
		return Generators.timeBasedGenerator(EthernetAddress.fromInterface()).generate();
	}
}
