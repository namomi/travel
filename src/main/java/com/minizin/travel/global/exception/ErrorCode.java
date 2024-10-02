package com.minizin.travel.global.exception;

/**
 * Class: BaseEntity Project: com.minizin.travel.global.exception
 * <p>
 * Description: ErrorCode
 *
 * @author JANG CHIHUN
 * @date 6/3/24 21:25 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */
public interface ErrorCode {
	int status();
	String message();
}
