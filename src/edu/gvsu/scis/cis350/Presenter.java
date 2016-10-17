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
	private static Scanner s = new Scanner(System.in, "UTF-8");
	
	/**
	 * The Presenter constructor.
	 * @param m This is the initial value of model
	 * @param v This is the initial value of view
	 */
	public Presenter(final Model m, final View v) {
		model = m;
		view = v;
		int whiteWins = 0;
		int blackWins = 0;
		System.out.println("Enter 'Quit' if you want to end the game "
				+ "or 'Restart' if you want to restart the game.");
		while (true) {
			while (!model.isGameOver()) {
				nextTurn();
			} 
			JOptionPane pane = new JOptionPane();
			JOptionPane.showMessageDialog(pane, model.getPlayer() 
					+ " wins!", "Reversi", JOptionPane.INFORMATION_MESSAGE);
			if (model.getPlayer() == Piece.BLACK) {
				blackWins++;
			} else if (model.getPlayer() == Piece.WHITE) {
				whiteWins++;
			}
			model = m;
			System.out.println("Current wins:");
			System.out.println("Black: " + blackWins);
			System.out.println("White: " + whiteWins);
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
	public final void getInput() {
		int x = -1;
		int y = -1;
		String input;
		
		while (x == -1) {
			System.out.print(model.getPlayer() + " Input x value");
			input = s.next();
			x = checkInput(input);
			if (x == -2) {
				model.changeTurn();
				return;
			}
		}
		x--;
		
		while (y == -1) {
			System.out.print(model.getPlayer() + " Input y value");
			input = s.next();
			y = checkInput(input);
			if (y == -2) {
				model.changeTurn();
				return;
			}
		}
		y--;
		
		if (placePiece(y, x)) {
				return;
		} else { //If the move is not valid
				System.out.println("Invalid move.");
				getInput();
		}
	}

	/**
	 * This method checks the user input to make sure that the input is valid.
	 * @param input This is the input from the user
	 * @return int Return the x or y value that the user inputed
	 */
	public final int checkInput(final String input) {
		if (input.toLowerCase().equals("quit")) {
			System.exit(0); 
		} else if (input.toLowerCase().equals("restart")) {
			model = new Model();
			return -2;
		} else {
			int z = -1;
			try {
				z = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Not a valid input");
				return z;
			}
			int size = model.getBoardSize();
			if (z >= 1 && z <= size) { //1-8
				return z;
			}
		}
		return 0;
	}

	/**
	 * This method checks for a game over.
	 * If it is not game over, the turn switches to the next player.
	 * @return This returns the next player
	 */
	public final Piece nextTurn() {
		model.print();
		getInput();
		model.changeTurn();	
		return model.getPlayer();
	}
}
