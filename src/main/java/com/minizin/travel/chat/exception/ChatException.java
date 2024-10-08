package com.minizin.travel.chat.exception;

import com.minizin.travel.global.exception.ErrorCode;

/**
 * Class: BaseEntity Project: package com.minizin.travel.board.exception
 * <p>
 * Description: BoardException
 *
 * @author JANG CHIHUN
 * @date 6/17/24 10:15 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */

public record ChatException(int status,
							String message) implements ErrorCode {
    public static final ChatException CHAT_NOT_FOUND = new ChatException(404, "chat not found");
	public static final ChatException INTERNAL_SERVER_ERROR = new ChatException(500, "internal server error");
}
