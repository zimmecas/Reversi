
public class Model {
	final int width = 8;
	final int height = 8;
	Piece[][] board;
	boolean player; //true for 1(white), false for 2(black)
	
	public Model(){
		makeBoard();
		fillBoard();
	}
	
	public void makeBoard(){
		board = new Piece[height][width];
	}
	
	public void fillBoard(){
		/*
		 * fills board with non-player pieces
		 * dual for loop
		 */
	}
	
	public boolean placePiece(int x, int y, Piece p){
		/*
		 * puts Piece "p" 
		 */
		if (isValidMove(x, y) == false){
			return false;
		} else {
			/*
			 * place piece
			 */
			return true;
		}
		
	}
	
	public boolean isValidMove(int x, int y){
		/*
		 * returns whether (x,y) has a piece or not
		 * and checks if move conforms to the rules of Reversi
		 */
		checkIfFlip(x,y);
		
		return false;
	}
	
	public boolean checkIfFlip(int x, int y){
		/*
		 * Checks if move flips any tiles
		 */
		
		return false;
	}
	
	public void Flip(){
		/*
		 * 1.piece gets placed
		 * 2.check for opposite colored piece in every direction
		 * 3. for every true #2, check row/column/diagonal until same colored piece in encountered
		 * 4. flip everything in between.  
		 */
	}
	
	public boolean isGameOver(){
		
		return true;
	}
	
	
}
