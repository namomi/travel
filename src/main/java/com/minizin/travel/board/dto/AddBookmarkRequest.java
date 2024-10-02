package com.minizin.travel.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * Class: BaseEntity Project: package com.minizin.travel.board.dto
 * <p>
 * Description: AddBookmarkRequest
 *
 * @author JANG CHIHUN
 * @date 6/17/24 10:15 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */

@Getter
@NoArgsConstructor
public class AddBookmarkRequest {
	private Long userId;
	private Long boardId;

	public AddBookmarkRequest(Long userId, Long boardId) {
		this.userId = userId;
		this.boardId = boardId;
	}
}
