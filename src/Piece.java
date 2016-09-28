	/**
	 * An enumeration for a player piece.
	 */
public enum  Piece {
	/**
	 * Signifies an empty space on the board.
	 */
	EMPTY, 
	/**
	 * Signifies an white piece on the board.
	 */
	WHITE,
	/**
	 * Signifies a black piece on the board.
	 */
	BLACK;
	
	/**
	 * Flips a piece from black to white or vice versa.
	 * @return This returns the opposite color or empty if it was empty
	 */
	public Piece flipPiece() {
		if (this == WHITE) {
			return BLACK;
		}
		if (this == BLACK) {
			return WHITE;
	}
		return EMPTY;
	}
}
