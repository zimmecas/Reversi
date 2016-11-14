package edu.gvsu.scis.cis350;

/**
 * This is class starts the Reversi game.
 * @author Brendan Dent, Casey Zimmerman, Caitlin Crowe
 * 
 */

public final class Main {
	/**
	 * This is the private constructor for the Main class.
	 */
	private Main() {
	}
	
	/**
	 * This method starts the game by creating the View and Model, 
	 * and passing them to the Presenter.
	 * @param args This is the default parameter for this method
	 */
	public static void main(final String[] args) {
		Model model = new Model();
		View view = new View();
		new Presenter(model, view);	
	}
}
