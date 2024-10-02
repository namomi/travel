package com.minizin.travel.board.dto;

import com.minizin.travel.board.entity.Board;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class: BaseEntity Project: package com.minizin.travel.board.dto
 * <p>
 * Description: AddBoardRequest
 *
 * @author JANG CHIHUN
 * @date 6/17/24 10:15 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */

@Getter
@NoArgsConstructor
public class AddBoardRequest {
	@NotNull
	private String title;
	@NotNull
	private String author;
	@NotNull
	private String period;
	@NotNull
	private int cost;
	@NotNull
	private String location;
	@NotNull
	private int members;

	@Builder
	public AddBoardRequest(String title, String author, String period, int cost, String location, int members) {
		this.title = title;
		this.author = author;
		this.period = period;
		this.cost = cost;
		this.location = location;
		this.members = members;
	}

	public Board toEntity(String author) {
		return Board.builder()
			.title(title)
			.author(author)
			.period(period)
			.cost(cost)
			.location(location)
			.members(members)
			.build();
	}
}
