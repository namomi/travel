package com.minizin.travel.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * Class: BaseEntity Project: package com.minizin.travel.board.dto
 * <p>
 * Description: UpdateBoardRequest
 *
 * @author JANG CHIHUN
 * @date 6/17/24 10:15 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */
@Getter
@NoArgsConstructor
public class UpdateBoardRequest {
	private String title;
	private String author;
	private String period;
	private int cost;
	private String location;
	private int members;

	@Builder
	public UpdateBoardRequest(String title, String author, String period, int cost, String location, int members) {
		this.title = title;
		this.author = author;
		this.period = period;
		this.cost = cost;
		this.location = location;
		this.members = members;
	}
}
