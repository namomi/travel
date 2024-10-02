package com.minizin.travel.board.exception;

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

public record BoardException(int status,
							 String message) implements ErrorCode {
    public static final BoardException BOARD_NOT_FOUND = new BoardException(404, "Board not found");
    public static final BoardException INVALID_BOARD_REQUEST = new BoardException(400, "Invalid board request");
}
