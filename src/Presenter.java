import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * 
 * @author Caitlin Crowe
 *
 */
public class Presenter {
	
	/**
	 * 
	 */
	private static Model model;
	/**
	 * 
	 */
	private View view;
	
	/**
	 * 
	 */
	private static Scanner s = new Scanner(System.in);
	/**
	 * The Presenter constructor.
	 * @param m is the initial value of model.
	 * @param v is the initial value of view.
	 */
	public Presenter(final Model m, final View v) {
		model = m;
		view = v;
		while(!model.isGameOver()){
			nextTurn();
		}
	}
	
	/**
	 * This method returns the view.
	 * @return view
	 */
	public final View getView() {
		return view;
	}
	
	/**
	 * This method sets the view.
	 * @param v is what view is set to.
	 */
	public final void setView(final View v) {
		view = v;
	}
	
	/**
	 * This method returns the model.
	 * @return model
	 */
	public final Model getModel() {
		return model;
	}
	
	/**
	 * This method sets the model.
	 * @param m is what model is set to.
	 */
	public final void setModel(final Model m) {
		model = m;
	}
	
	/**
	 * This method places a piece.
	 * @param x is the x position of where to place the piece.
	 * @param y is the y position of where to place the piece.
	 * @return model.placePiece(x, y)
	 */
	private final static boolean placePiece(final int x, final int y) {
		return model.placePiece(x, y);
	}
	
	public static void getInput(){
		System.out.print("Input x value");
		int x = s.nextInt();
		x--;
		System.out.print("Input y value");
		int y = s.nextInt();
		y--;
		while (!placePiece(y,x)){
			System.out.print("Please enter a valid piece location");
			x = s.nextInt();
			x--;
			y = s.nextInt();
			y--;
		}
		
	}
	
	public static Piece nextTurn(){
		if(!model.isGameOver()){
			getInput();
			model.print();
			model.changeTurn();	
		}
		else {
			JOptionPane pane = new JOptionPane();
			JOptionPane.showMessageDialog(pane, model.getPlayer() + " wins!", "Reversi", JOptionPane.INFORMATION_MESSAGE);
		}
		return model.getPlayer();
			
		
	
	}
		public static void main(){
			
			
		}
}
