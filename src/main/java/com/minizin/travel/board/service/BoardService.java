package com.minizin.travel.board.service;

import com.minizin.travel.board.dto.UpdateBoardRequest;
import com.minizin.travel.board.entity.Board;
import com.minizin.travel.board.repository.BoardRepository;
import com.minizin.travel.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.minizin.travel.board.exception.BoardException.BOARD_NOT_FOUND;
import static com.minizin.travel.board.exception.BoardException.INVALID_BOARD_REQUEST;

/**
 * Class: BaseEntity Project: package com.minizin.travel.board.service
 * <p>
 * Description: BoardService
 *
 * @author JANG CHIHUN
 * @date 6/17/24 10:15 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	@Transactional(readOnly = true)
	public List<Board> findAll() {
		return boardRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Board findById(Long id) {
		return boardRepository.findById(id)
			.orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));
	}

	@Transactional
	public Board save(Board board) {
		return boardRepository.save(board);
	}

	@Transactional
	public void delete(Long id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public Board update(Long id, UpdateBoardRequest request) {
		Board existingBoard = findById(id);

		existingBoard.updateBoard(
			request.getTitle() != null ? request.getTitle() : existingBoard.getTitle(),
			request.getPeriod() != null ? request.getPeriod() : existingBoard.getPeriod(),
			request.getCost() != 0 ? request.getCost() : existingBoard.getCost(),
			request.getLocation() != null ? request.getLocation() : existingBoard.getLocation(),
			request.getMembers() != 0 ? request.getMembers() : existingBoard.getMembers()
		);

		return existingBoard;
	}

	public void verifyAuthor(Long id, String author) {
		Board board = findById(id);
		if (!board.getAuthor().equals(author)) {
			throw new CustomException(INVALID_BOARD_REQUEST);
		}
	}
}
