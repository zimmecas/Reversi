package edu.gvsu.scis.cis350;
/**
 * This class controls the board.
 * @author Brendan Dent, Casey Zimmerman, Caitlin Crowe
 *
 */
public class Model {
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
					System.out.print("{0}" + " ");
				}
				if (board[j][i] == Piece.BLACK) {
					System.out.print("{1}" + " ");
				}
				if (board[j][i] == Piece.WHITE) {
					System.out.print("{2}" + " ");
				}
			}
			System.out.println();
		}
	}

	/**
	 * This method makes a new height x width board.
	 */
	public final void makeBoard() {
		board = new Piece[HEIGHT][WIDTH];
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
	public final boolean checkIfFlip(final int x, final int y) {
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
	public final boolean checkDirection(final int x, final int y,
			final int xDir, final int yDir) {
		int yDisp = yDir;
		int xDisp = xDir;
		while (y + yDisp > 0 && y + yDisp < HEIGHT - 1 
				&& x + xDisp > 0 && x + xDisp < WIDTH - 1
				&& board[y + yDisp][x + xDisp] == player.flipPiece() 
				) {
			yDisp += yDir;
			xDisp += xDir;
			if (board[y + yDisp][x + xDisp] == player) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method checks a direction and flips all necessary pieces.
	 * @param x This is the horizontal position of placed piece
	 * @param y This is the vertical position of placed piece
	 * @param xDir This is a number between -1 and 1 to indicate x direction
	 * @param yDir This is a number between -1 and 1 to indicate y direction
	 * @return This returns true if flip completed successfully
	 */
	public final boolean flipDirection(final int x, final int y,
			final int xDir, final int yDir) {
		int yDisp = yDir;
		int xDisp = xDir;
		while (board[y + yDisp][x + xDisp] == player.flipPiece()
				&& y + yDisp > 0 && y + yDisp < HEIGHT - 1 
				&& x + xDisp > 0 && x + xDisp < WIDTH - 1) {
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
	public final void flip(final int x, final int y) {
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
				changeTurn();
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
}
