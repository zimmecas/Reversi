package edu.gvsu.scis.cis350;

import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * This class communicates between the View and Model to run the game.
 * @author Brendan Dent, Casey Zimmerman, Caitlin Crowe
 *
 */
public class Presenter {

	/**
	 * This variable is used to communicate with the Model class.
	 */
	private static Model model;
	
	/**
	 * This variable is used to communicate with the View class.
	 */
	private View view;

	/**
	 * This variable is used for scanning in user input.
	 */
	private static Scanner s = new Scanner(System.in);
	
	/**
	 * The Presenter constructor.
	 * @param m This is the initial value of model
	 * @param v This is the initial value of view
	 */
	public Presenter(final Model m, final View v) {
		model = m;
		view = v;
		while (!model.isGameOver()) {
			nextTurn();
		}
	}

	/**
	 * This method returns the view.
	 * @return view This returns the current value of view
	 */
	public final View getView() {
		return view;
	}

	/**
	 * This method sets the view.
	 * @param v This is a new value that view will be set equal to
	 */
	public final void setView(final View v) {
		view = v;
	}

	/**
	 * This method returns the model.
	 * @return model This returns the current value of model
	 */
	public final Model getModel() {
		return model;
	}

	/**
	 * This method sets the model.
	 * @param m This is the new value that model will be set equal to
	 */
	public final void setModel(final Model m) {
		model = m;
	}

	/**
	 * This method places a piece.
	 * @param x This is the x position of where to place the piece
	 * @param y This is the y position of where to place the piece
	 * @return This returns true if the piece was placed, return false otherwise
	 */
	private static boolean placePiece(final int x, final int y) {
		return model.placePiece(x, y);
	}

	/**
	 * This method receives the user's input and 
	 * confirms that the input is valid.
	 */
	public static void getInput() {
		System.out.print("Input x value");
		int x = s.nextInt();
		x--;
		System.out.print("Input y value");
		int y = s.nextInt();
		y--;
		while (!placePiece(y, x)) {
			System.out.print("Please enter a valid piece location");
			x = s.nextInt();
			x--;
			y = s.nextInt();
			y--;
		}
	}

	/**
	 * This method checks for a game over.
	 * If it is not game over, the turn switches to the next player.
	 * @return This returns the next player
	 */
	public static Piece nextTurn() {
		if (!model.isGameOver()) {
			getInput();
			model.print();
			model.changeTurn();	
		} else {
			JOptionPane pane = new JOptionPane();
			JOptionPane.showMessageDialog(pane, model.getPlayer() 
					+ " wins!", "Reversi", JOptionPane.INFORMATION_MESSAGE);
		}
		return model.getPlayer();
	}
}
