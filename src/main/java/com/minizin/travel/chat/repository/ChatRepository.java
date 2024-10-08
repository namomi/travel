package com.minizin.travel.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.minizin.travel.chat.entity.Chat;

import io.lettuce.core.dynamic.annotation.Param;

public interface ChatRepository extends JpaRepository<Chat, Long> {

	@Query(value = "SELECT * FROM  chats c WHERE JSON_CONTAINS(c.userIds, : userIdJson, '$')", nativeQuery = true)
	List<Chat> findAllByJson(@Param("userIdJson") String userIdJson);
}
