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
		fail("Not yet implemented");
	}

	@Test
	public final void testCheckIfFlip() {
		fail("Not yet implemented");
	}

	@Test
	public final void testCheckDirection() {
		fail("Not yet implemented");
	}

	@Test
	public final void testFlipDirection() {
		fail("Not yet implemented");
	}

	@Test
	public final void testFlip() {
		fail("Not yet implemented");
	}

	@Test
	public final void testIsGameOver() {
		fail("Not yet implemented");
	}

	@Test
	public final void testAnyMovesLeft() {
		fail("Not yet implemented");
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
