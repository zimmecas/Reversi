package edu.gvsu.scis.cis350;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ModelTest {
	
	Model m;

	@Before
	public final void setUp() throws Exception {
		m = new Model();
	}


	@Test
	public final void testFillBoard() {
		Piece[][] p = new Piece[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				p[j][i] = Piece.EMPTY;
			}
		}
		p[4][3] = Piece.BLACK;
		p[3][4] = Piece.BLACK;
		p[3][3] = Piece.WHITE;
		p[4][4] = Piece.WHITE;	
		assertArrayEquals("boards not equal", p, m.getBoard());
	}
	
	@Test
	public final void testPlacePiece1() {
		assertEquals("Can place a piece at 3,2", m.placePiece(3,2), true);
	}
	@Test
	public final void testPlacePiece2() {
		assertEquals("Cannot place piece at 4,4", m.placePiece(4,4), false);
	}

	@Test
	public final void testIsValidMove() {
		assertEquals("Piece at 3,2 should be a valid move", m.isValidMove(3,2), true);
	}
	
	@Test
	public final void testIsValidMove1() {
		m.placePiece(3, 2);
		m.changeTurn();
		assertEquals("Piece at 2,2 should be valid move", m.isValidMove(2,2), true);
	}
	
	@Test
	public final void testIsValidMove2() {
		m.placePiece(3, 2);
		assertEquals("Another piece at 3,2 should be invalid move", m.isValidMove(3,2), false);
	}
	@Test
	public final void testIsValidMove3() {
		m.placePiece(3, 2);
		assertEquals("Another piece at 0,0 should be invalid move", m.isValidMove(0,0), false);
	}

	@Test
	public final void testIsGameOver() {
		m.makeEndGame1();
		assertTrue(m.isGameOver());
	}
	
	@Test
	public final void testIsGameOver2() {
		assertFalse(m.isGameOver());
	}

	@Test
	public final void testAnyMovesLeft() {
		assertEquals("Should be moves left", m.anyMovesLeft(), true);
	}
	
	@Test
	public final void testAnyMovesLeft1() {
		m.placePiece(3, 2);
		m.placePiece(4, 5);
		assertEquals("Should be not be moves left", m.anyMovesLeft(), false);
	}
	
	@Test
	public final void testAnyMovesLeft2() {
		m.makeEndGame1();
		assertEquals("Should be not be any moves left", m.anyMovesLeft(), 
				false);
	}
	
	@Test
	public final void testAnyMovesLeft3() {
		m.makeEndGame2();
		assertEquals("Should be not be any moves left", m.anyMovesLeft(), 
				false);
	}
	@Test
	public final void testAnyMovesLeft4() {
		m.makeEndGame3();
		assertEquals("Should be not be any moves left", m.anyMovesLeft(), 
				false);
	}

	@Test
	public final void testChangeTurn1() {
		m.placePiece(3, 2);
		m.changeTurn(); //now white's turn
		m.placePiece(2, 4);
		m.changeTurn(); //now black's turn
		assertEquals("turn did not change", m.getPlayer(), Piece.BLACK);
	}
	@Test
	public final void testChangeTurn2() {
		m.placePiece(3, 2);
		m.changeTurn();
		assertEquals("turn did not change", m.getPlayer(), Piece.WHITE);
	}

	@Test
	public final void testGetPlayer() {
		assertEquals("players not equal", m.getPlayer(), Piece.BLACK);
	}
	
	@Test
	public final void testGetPlayer1() {
		m.placePiece(3, 2);
		m.changeTurn();
		assertEquals("players not equal", m.getPlayer(), Piece.WHITE);
	}
	
	@Test
	public final void testCountPieces() {
		int[] arr = new int[3];
		arr[0] = 60;
		arr[1] = 2;
		arr[2] = 2;		
		assertArrayEquals("results array not equal", m.countPieces(), arr);
	}
	
	@Test
	public final void testCountPieces1() {
		int[] arr = new int[3];
		arr[0] = 59;
		arr[1] = 4;
		arr[2] = 1;	
		m.placePiece(3, 2);
		assertArrayEquals("results array not equal", m.countPieces(), arr);
	}
	
	@Test
	public final void testGetBoardSize() {
		assertEquals("board width/height should equal 8", m.getBoardSize(), 8);
	}
	@Test
	public final void testBestMove() {
		assertEquals("best move should be 2, 3", m.bestMove()[1],  2);
		assertEquals("best move should be 2, 3", m.bestMove()[2],  3);
	}
}
