package com.minizin.travel.board.dto;

import com.minizin.travel.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

/**
 * Class: BaseEntity Project: package com.minizin.travel.board.dto
 * <p>
 * Description: AddBoardResponse
 *
 * @author JANG CHIHUN
 * @date 6/17/24 10:15 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */


@Getter
public class AddBoardResponse {
	private Long id;
	private String title;
	private String author;
	private String period;
	private int cost;
	private String location;
	private int members;

	@Builder
	public AddBoardResponse(Long id, String title, String author, String period, int cost, String location, int members) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.period = period;
		this.cost = cost;
		this.location = location;
		this.members = members;
	}

	public static AddBoardResponse fromEntity(Board board) {
		return AddBoardResponse.builder()
			.id(board.getId())
			.title(board.getTitle())
			.author(board.getAuthor())
			.period(board.getPeriod())
			.cost(board.getCost())
			.location(board.getLocation())
			.members(board.getMembers())
			.build();
	}
}
