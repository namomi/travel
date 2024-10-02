package com.minizin.travel.board.controller;

import com.minizin.travel.board.dto.AddBoardRequest;
import com.minizin.travel.board.dto.AddBoardResponse;
import com.minizin.travel.board.dto.UpdateBoardRequest;
import com.minizin.travel.board.entity.Board;
import com.minizin.travel.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Class: BaseEntity Project: package com.minizin.travel.board.controller
 * <p>
 * Description: BoardController
 *
 * @author JANG CHIHUN
 * @date 6/17/24 10:15 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */

@RestController
@RequiredArgsConstructor
public class BoardController {

	private BoardService boardService;

	@GetMapping("/travels")
	public List<Board> getAllBoards() {
		return boardService.findAll();
	}

	@GetMapping("/travels/{id}")
	public Board getBoardById(@PathVariable Long id) {
		return boardService.findById(id);
	}

	@PostMapping("/travels/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public AddBoardResponse createBoard(@RequestBody AddBoardRequest request,  Principal principal) {
		Board board = request.toEntity(principal.getName());
		Board savedBoard = boardService.save(board);
		return AddBoardResponse.fromEntity(savedBoard);
	}

	@PutMapping("/travels/{id}")
	public AddBoardResponse updateBoard(@PathVariable Long id, @RequestBody UpdateBoardRequest request,
		Principal principal) {

		boardService.verifyAuthor(id, principal.getName());
		Board updatedBoard = boardService.update(id, request);
		return AddBoardResponse.fromEntity(updatedBoard);
	}

	@DeleteMapping("/travels/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBoard(@PathVariable Long id, Principal principal) {
		boardService.verifyAuthor(id, principal.getName());
		boardService.delete(id);
	}
}
