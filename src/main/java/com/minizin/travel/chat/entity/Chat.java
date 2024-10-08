package com.minizin.travel.chat.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "chats")
public class Chat {

    private static final int DIRECT_CHAT_USER_SIZE = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatType chatType;

    @Convert(converter = UserIdsConverter.class)
    private List<Long> userIds;

    public boolean alone() {
        return userIds.size() != DIRECT_CHAT_USER_SIZE;
    }

    public void leave(Long userId) {
        userIds.remove(userId);
    }

    @Converter
    static
    class UserIdsConverter implements AttributeConverter<List<Long>, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(List<Long> attribute) {
            try {
                return objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Error converting list to JSON", e);
            }
        }

        @Override
        public List<Long> convertToEntityAttribute(String s) {
            try {
                return objectMapper.readValue(s,  new TypeReference<>() {});
            } catch (IOException e) {
                throw new IllegalArgumentException("Error converting JSON to list", e);
            }
        }
    }
}
