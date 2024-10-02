package com.minizin.travel.board.service;

import com.minizin.travel.board.dto.UpdateBoardRequest;
import com.minizin.travel.board.entity.Board;
import com.minizin.travel.board.repository.BoardRepository;
import com.minizin.travel.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardServiceTest {
	@Mock
	private BoardRepository boardRepository;

	@InjectMocks
	private BoardService boardService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void FindAll() {
		//given
		List<Board> boards = List.of(new Board(), new Board());
		when(boardRepository.findAll()).thenReturn(boards);

		//when
		List<Board> result = boardService.findAll();

		//then
		assertEquals(boards.size(), result.size());
		verify(boardRepository, times(1)).findAll();
	}

	@Test
	void FindById() {
		//given
		Board board = Board.builder().id(1L).build();
		when(boardRepository.findById(1L)).thenReturn(Optional.of(board));

		//when
		Board result = boardService.findById(1L);

		//then
		assertEquals(board.getId(), result.getId());
		verify(boardRepository, times(1)).findById(1L);
	}

	@Test
	void testFindById_NotFound() {
		//given
		//when
		when(boardRepository.findById(1L)).thenReturn(Optional.empty());

		//then
		assertThrows(CustomException.class, () -> boardService.findById(1L));
		verify(boardRepository, times(1)).findById(1L);
	}

	@Test
	void testSave() {
		//given
		Board board = Board.builder().id(1L).build();
		when(boardRepository.save(board)).thenReturn(board);

		//when
		Board result = boardService.save(board);

		//then
		assertEquals(board.getId(), result.getId());
		verify(boardRepository, times(1)).save(board);
	}

	@Test
	void testDelete() {
		//given
		doNothing().when(boardRepository).deleteById(1L);

		//when
		boardService.delete(1L);

		//then
		verify(boardRepository, times(1)).deleteById(1L);
	}

	 @Test
	 void testUpdate() {
	 	Board board = Board.builder().id(1L).title("Old Title").build();
	 	UpdateBoardRequest request = new UpdateBoardRequest("New Title", null, "2019-03-01",100, null, 0);
	 	when(boardRepository.findById(1L)).thenReturn(Optional.of(board));

	 	Board result = boardService.update(1L, request);

	 	assertEquals(request.getTitle(), result.getTitle());
	 	verify(boardRepository, times(1)).findById(1L);
	 }

	@Test
	void testVerifyAuthor_Valid() {
		//given
		Board board = Board.builder().id(1L).author("author").build();
		when(boardRepository.findById(1L)).thenReturn(Optional.of(board));

		//then
		assertDoesNotThrow(() -> boardService.verifyAuthor(1L, "author"));
		verify(boardRepository, times(1)).findById(1L);
	}

	@Test
	void testVerifyAuthor_Invalid() {
		//given
		Board board = Board.builder().id(1L).author("author").build();
		when(boardRepository.findById(1L)).thenReturn(Optional.of(board));

		//then
		assertThrows(CustomException.class, () -> boardService.verifyAuthor(1L, "wrongAuthor"));
		verify(boardRepository, times(1)).findById(1L);
	}
}
