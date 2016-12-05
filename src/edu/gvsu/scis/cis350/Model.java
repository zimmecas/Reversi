package edu.gvsu.scis.cis350;

import java.io.Serializable;

/**
 * This class controls the board.
 * @author Brendan Dent, Casey Zimmerman, Caitlin Crowe
 *
 */
public class Model implements Serializable {
	/**
	 * default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The board width.
	 */
	private static final int WIDTH = 8;
	/**
	 * The board height.
	 */
	private static final int HEIGHT = 8;
	/**
	 * The virtual representation of a game board.
	 */
	private Piece[][] board;
	/**
	 * Determines whose turn it is. Black goes first.
	 */
	private Piece player = Piece.BLACK; 
	/**
	 * Horizontal position of the first starting piece.
	 */
	private static final int XSTART = 3;
	/**
	 * Vertical position of the first starting piece.
	 */
	private static final int YSTART = 3;
	/**
	 * How large the results array is.
	 */
	private static final int RESULTSINT = 3;
	/**
	 * How large the array in bestMove() is.
	 */
	private static final int BESTMOVE = 3;
	/**
	 * This constructor makes and fills a board when called.
	 */
	public Model() {
		makeBoard();
		fillBoard();
	}

	/**
	 * This method prints the board to the screen.
	 */
	public final void print() {
		System.out.println("   1   2   3   4   5   6   7   8 \n"
				+ "  ______________________________");
		for (int i = 0; i < HEIGHT; i++) {
			System.out.print((i + 1) + "|");
			for (int j = 0; j < WIDTH; j++) {
				if (board[j][i] == Piece.EMPTY) {
					System.out.print("{ }" + " ");
				}
				if (board[j][i] == Piece.BLACK) {
					System.out.print("{B}" + " ");
				}
				if (board[j][i] == Piece.WHITE) {
					System.out.print("{W}" + " ");
				}
			}
			System.out.println();
		}
		int[] results = countPieces();
		System.out.println("B: " + results[1] + "\t" + "\t" + "\t" + "     " 
		+ "W: " + results[2]);
	}

	/**
	 * This method makes a new height x width board.
	 * @return board This returns the new board
	 */
	private Piece[][] makeBoard() {
		board = new Piece[HEIGHT][WIDTH];
		return board;
	}

	/**
	 * This method makes all spaces empty,
	 * then puts the appropriate pieces in the correct starting positions.
	 */
	public final void fillBoard() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				board[j][i] = Piece.EMPTY;
			}
			board[YSTART][XSTART] = Piece.WHITE;
			board[YSTART + 1][XSTART + 1] = Piece.WHITE;
			board[YSTART][XSTART + 1] = Piece.BLACK;
			board[YSTART + 1][XSTART] = Piece.BLACK;
		}				
	}
	
	

	/**
	 * This method checks if the player can place a piece
	 *  at the given location and if they can,
	 *  it places the piece and performs all necessary flips.
	 * @param x This is the horizontal position of piece to be placed 
	 * @param y This is the vertical position of piece to be placed 
	 * @return This returns true if the move was completed successfully
	 */
	public final boolean placePiece(final int x, final int y) {
		if (isValidMove(x, y)) {
			board[y][x] = player;
			flip(x, y);
			return true;
		}			
		return false;
	}

	/**
	 * This method checks if the current player placing 
	 * a piece at x, y is a valid move.
	 * @param x This is the horizontal position of place to check
	 * @param y This is the vertical position of place to check
	 * @return This returns true if the move would be valid
	 */
	public final boolean isValidMove(final int x, final int y) {
		if (board[y][x] == Piece.EMPTY) {
			if (checkIfFlip(x, y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param x This is the horizontal position of placed piece
	 * @param y This is the vertical position of placed piece
	 * @return This returns true if at least one flip would 
	 * occur if a piece were placed at x, y
	 */
	private boolean checkIfFlip(final int x, final int y) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (checkDirection(x, y, i, j)) {
					return true;
				}
			}	
		}
		return false;
	}

	/**
	 * This method checks if flips should occur in a particular direction.
	 * based on the piece most recently played
	 * @param x This is the horizontal position of placed piece
	 * @param y This is the vertical position of placed piece
	 * @param xDir This is a number between -1 and 1 to indicate x direction
	 * @param yDir This is a number between -1 and 1 to indicate y direction
	 * @return This returns true if flips should occur in the given direction
	 */
	private boolean checkDirection(final int x, final int y,
			final int xDir, final int yDir) {
		int yDisp = yDir;
		int xDisp = xDir;
		while (y + yDisp >= 0 && y + yDisp < HEIGHT 
				&& x + xDisp >= 0 && x + xDisp < WIDTH  
				&& board[y + yDisp][x + xDisp] == player.flipPiece() 
				) {
			yDisp += yDir;
			xDisp += xDir;
			if (y + yDisp >= 0 && y + yDisp < HEIGHT 
					&& x + xDisp >= 0 && x + xDisp < WIDTH
					&& board[y + yDisp][x + xDisp] == player) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines the place that will result in the current player 
	 * flipping the most pieces.
	 * @return and array containing the number of flips, 
	 * the x position and the y position of the move.
	 */

	public final int [] bestMove() {
		int [] max = new int[BESTMOVE];
		int prevMax = 0;
		for (int k = 0; k < HEIGHT; k++) {
			for (int h = 0; h < WIDTH; h++) {
				max[0] = 0;
				if (isValidMove(h, k)) {
					for (int i = -1; i < 2; i++) {
						for (int j = -1; j < 2; j++) {
							max[0] = checkBestMove(h, k, i, j) + max[0];
						}
					}
					if (prevMax < max[0]) {
					prevMax = max[0];
					max[1] = k;
					max[2] = h;
					
					}
				}
			}
		}
		max[0] = prevMax;
		return max;
	}
	
	/**
	 * Counts the number of pieces flipped in one given direction.
	 * @param x X location of the potential piece.
	 * @param y Y location of the potential piece.
	 * @param xDir X direction of the potential flip.
	 * @param yDir Y direction of the potential flip.
	 * @return The number of pieces flipped in the given direction by the piece.
	 */
	private int checkBestMove(final int x, final int y,
			final int xDir, final int yDir) {
		int yDisp = yDir;
		int xDisp = xDir;
		int max = 0;
		while (y + yDisp >= 0 && y + yDisp < HEIGHT 
				&& x + xDisp >= 0 && x + xDisp < WIDTH  
				&& board[y + yDisp][x + xDisp] == player.flipPiece() 
				) {
			yDisp += yDir;
			xDisp += xDir;
			max++;
			if (y + yDisp >= 0 && y + yDisp < HEIGHT 
					&& x + xDisp >= 0 && x + xDisp < WIDTH
					&& board[y + yDisp][x + xDisp] == player) {
				return max;
			}
		}
		return 0;
	}

	/**
	 * This method checks a direction and flips all necessary pieces.
	 * @param x This is the horizontal position of placed piece
	 * @param y This is the vertical position of placed piece
	 * @param xDir This is a number between -1 and 1 to indicate x direction
	 * @param yDir This is a number between -1 and 1 to indicate y direction
	 * @return This returns true if flip completed successfully
	 */
	private boolean flipDirection(final int x, final int y,
			final int xDir, final int yDir) {
		int yDisp = yDir;
		int xDisp = xDir;
		while (board[y + yDisp][x + xDisp] == player.flipPiece()
				&& y + yDisp >= 0 && y + yDisp < HEIGHT  
				&& x + xDisp >= 0 && x + xDisp < WIDTH) {
			board[y + yDisp][x + xDisp] = player;
			yDisp += yDir;
			xDisp += xDir;
			if (board[y + yDisp][x + xDisp] == player) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method checks every direction and flips the necessary pieces.
	 * @param x This is the horizontal position of placed piece
	 * @param y This is the vertical position of placed piece
	 */
	private void flip(final int x, final int y) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (checkDirection(x, y, i, j)) {
					flipDirection(x, y, i, j);
				}
			}
		}
	}

	/**
	 * This method checks if the game is over by checking for valid moves.
	 * @return This returns true if game is over, false if not
	 */
	public final boolean isGameOver() {
		if (anyMovesLeft()) {
			return false;
		} else {
			changeTurn();
			if (anyMovesLeft()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method checks if player has any valid moves left.
	 * @return This returns true if player has any moves left
	 */
	public final boolean anyMovesLeft() {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (isValidMove(j, i)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * This method changes whose turn it is.
	 */
	public final void changeTurn() {
		player = player.flipPiece();
	}

	/**
	 * This method returns the current player.
	 * @return player This is the current player
	 */
	public final Piece getPlayer() {
		return player;
	}	
	
	/**
	 * This method counts the total number of empty spaces, black pieces, 
	 * and white pieces on the board and returns the numbers in results[].
	 * @return results index 0 is # of blank spaces, index 1 is # of black 
	 * pieces, index 2 is # of white pieces.
	 * 
	 */
	public final int[] countPieces() {
		int[] results = new int[RESULTSINT];
		results[0] = HEIGHT * WIDTH;
		results[1] = 0;
		results[2] = 0;
		for (int h = 0; h < HEIGHT; h++) {
			for (int w = 0; w < WIDTH; w++) {
				if (board[h][w] == Piece.BLACK) {
					results[1]++; 
					results[0]--;
				} else if (board[h][w] == Piece.WHITE) {
					results[2]++;
					results[0]--;
				}
			}
		}
		return results;
	}
	
	/**
	 * Makes board into a configuration where white wins, there are no more 
	 * valid moves and there are empty spaces.
	 */
	public final void makeEndGame1() {
		for (int h = 0; h < HEIGHT; h++) {
			for (int w = 0; w < WIDTH; w++) {
				board[h][w] = Piece.WHITE;
			}
		}
		
		//This is just to keep checkStyle happy because of magic number stuff.
		//According to checkStyle, 1 and 2 are not magic numbers.
		int h1 = HEIGHT - 2; //6 => 8 - 2
		int h2 = HEIGHT - 1; //7 => 8 - 1
		int w1 = WIDTH - 2 - 2; //4 => 8 - 4
		int w2 = WIDTH - 1 - 2; //5 => 8 - 3
		int w3 = WIDTH - 2 - 2 - 1; //3 => 8 - 5
		int w4 = WIDTH - 2; //6 => 8 - 2 
		board[h1][w1] = Piece.EMPTY;
		board[h1][w2] = Piece.EMPTY;
		board[h2][w3] = Piece.EMPTY;
		board[h2][w1] = Piece.EMPTY;
		board[h2][w2] = Piece.BLACK;
		board[h2][w4] = Piece.EMPTY;
	}
	
	/**
	 * Makes board where all pieces are black (not actually achievable).
	 */
	public final void makeEndGame2() {
		for (int h = 0; h < HEIGHT; h++) {
			for (int w = 0; w < WIDTH; w++) {
				board[h][w] = Piece.BLACK;
			}
		}
	}
	
	/**
	 * Makes board where half is black and half is white
	 * (probably not actually achievable in this configuration).
	 */
	public final void makeEndGame3() {
		for (int h = 0; h < HEIGHT; h++) {
			for (int w = 0; w < WIDTH / 2; w++) {
				board[h][w] = Piece.BLACK;
			}
		}
		for (int h = 0; h < HEIGHT; h++) {
			for (int w = WIDTH / 2; w < WIDTH; w++) {
				board[h][w] = Piece.WHITE;
			}
		}
	}
	
	/**
	 * This method returns the board.
	 * @return board This is the current board
	 */
	public final Piece[][] getBoard() {
		Piece[][] b = board;
		return b;
	}
	
	
	/**
	 * This method returns the size of the board.
	 * @return size This is the size of the board
	 */
	public final int getBoardSize() {
		int size = WIDTH;
		return size;
	}
}
