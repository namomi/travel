package com.minizin.travel.global.exception;

import lombok.Getter;

/**
 * Class: BaseEntity Project: com.minizin.travel.global.exception
 * <p>
 * Description: CustomException
 *
 * @author JANG CHIHUN
 * @date 6/3/24 21:25 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */
@Getter
public class CustomException extends RuntimeException {
	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.message());
		this.errorCode = errorCode;
	}
}
