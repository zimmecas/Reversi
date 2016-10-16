package edu.gvsu.scis.cis350;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestModel {
	
	Model m;

	@Before
	public final void setUp() throws Exception {
		m = new Model();
	}

	@Test
	public final void testModel() {
		fail("Not yet implemented");
	}

	@Test
	public final void testPrint() {
		fail("Not yet implemented");
	}

	@Test
	public final void testMakeBoard() {
		fail("Not yet implemented");
	}

	@Test
	public final void testFillBoard() {
		fail("Not yet implemented");
	}

	@Test
	public final void testPlacePiece() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
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
		assertEquals("Should be not be any moves left", m.anyMovesLeft(), false);
	}
	
	@Test
	public final void testAnyMovesLeft3() {
		m.makeEndGame2();
		assertEquals("Should be not be any moves left", m.anyMovesLeft(), false);
	}
	@Test
	public final void testAnyMovesLeft4() {
		m.makeEndGame3();
		assertEquals("Should be not be any moves left", m.anyMovesLeft(), false);
	}

	@Test
	public final void testChangeTurn() {
		fail("Not yet implemented");
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
}
