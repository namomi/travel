package com.minizin.travel.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * Class: BaseEntity Project: package com.minizin.travel.board.entity
 * <p>
 * Description: Board
 *
 * @author JANG CHIHUN
 * @date 6/17/24 10:15 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String author;
	private String period;
	private int cost;
	private String location;
	private int members;

	public void updateBoard(String title, String period, int cost, String location, int members) {
		this.title = title;
		this.period = period;
		this.cost = cost;
		this.location = location;
		this.members = members;
	}
}
