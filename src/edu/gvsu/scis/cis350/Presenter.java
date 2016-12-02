package edu.gvsu.scis.cis350;

import java.util.Scanner;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * This class communicates between the View and Model to run the game.
 * @author Brendan Dent, Casey Zimmerman, Caitlin Crowe
 *
 */
public class Presenter {

	/**
	 * This variable is used to communicate with the Model class.
	 */
	private Model model;

	/**
	 * This variable is used to communicate with the View class.
	 */
	private View view;

	/**
	 * This variable is used for scanning in user input.
	 */
	private static Scanner s = new Scanner(System.in, "UTF-8");
	

	final JFileChooser fc = new JFileChooser();

	/**
	 * Used to help catch invalid inputs.
	 */
	private static final int FLAG = -2;

	/**
	 * Indicates if player vs player or player vs computer
	 */
	private boolean pvp = true;

	/**
	 * The Presenter constructor.
	 * @param m This is the initial value of model
	 * @param v This is the initial value of view
	 */
	public Presenter(final Model m, final View v) {
		model = m;
		view = v;

		view.updateBoard(model.getBoard());
		

		view.addBoardActionListeners(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = view.getButtonRow(e.getSource());
				int col = view.getButtonCol(e.getSource());
				nextTurnGUI(col, row);
				int[] count = model.countPieces();
				view.updateBoard(model.getBoard());
				view.updateCurrentPlayer(model.getPlayer(), count[1], count[2]);
				if (model.isGameOver()) { //This does not seem to work
					System.out.println("!!!!Game over detected by Presenter!!!!");
					if (model.countPieces()[1] > model.countPieces()[2]) {
						System.out.println("Player 1 wins!");
						view.updateWinsPanel(1, 0, false);
					} else if (model.countPieces()[2] > model.countPieces()[1]) {
						System.out.println("Player 2 wins!");
						view.updateWinsPanel(0, 1, false);
					} else {
						System.out.println("Tie!");
						view.updateWinsPanel(1, 1, true);
					}
					newGame();
					count = model.countPieces();
					view.updateBoard(model.getBoard());
					view.updateCurrentPlayer(model.getPlayer(), count[1], count[2]);
				}
				//System.out.println(model.getPlayer()+" turn.");
			}
		});

		view.addQuitActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0); 
			}
		});

		view.addNewGameActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
				int[] count = model.countPieces();
				view.updateBoard(model.getBoard());
				view.updateCurrentPlayer(model.getPlayer(), count[1], count[2]);
			}
		});
		
		view.addPVPActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pvp = true;
				newGame();
				int[] count = model.countPieces();
				view.updateBoard(model.getBoard());
				view.updateCurrentPlayer(model.getPlayer(), count[1], count[2]);
			}
		});
		
		view.addPVCActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pvp = false;
				newGame();
				int[] count = model.countPieces();
				view.updateBoard(model.getBoard());
				view.updateCurrentPlayer(model.getPlayer(), count[1], count[2]);
			}
		});	
		
		view.addSaveActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			try {
				if(fc.showSaveDialog(view.frame) == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
				FileOutputStream saveFile = new FileOutputStream(file);
				ObjectOutputStream save = new ObjectOutputStream(saveFile);
				save.writeObject(model);
				save.writeObject(view.b);
				save.writeObject(view.w);
				save.writeObject(model.getPlayer());
				save.writeObject(pvp);
				save.close();
				}
				} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			}
		});	
		
		view.addLoadActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(fc.showOpenDialog(view.frame) == JFileChooser.APPROVE_OPTION){
						File file = fc.getSelectedFile();
					FileInputStream loadFile = new FileInputStream(file);
					ObjectInputStream load = new ObjectInputStream(loadFile);
					model = (Model) load.readObject();
					view.b = (int) load.readObject();
					view.w = (int) load.readObject();
					if(model.getPlayer() != (Piece) load.readObject())
						model.changeTurn();
					pvp = (Boolean) load.readObject();
					int[] count = model.countPieces();
					view.updateBoard(model.getBoard());
					view.updateCurrentPlayer(model.getPlayer(), count[1], count[2]);
					load.close();
					}
					} catch (Exception e1) {
					e1.printStackTrace();
				} 
			}
		});	
		
		view.addCustomActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.background = JColorChooser.showDialog(null, "Choose a Background Color", view.background);
				view.p1Color = JColorChooser.showDialog(null, "Choose a Color for Player 1", view.p1Color);
				view.p2Color = JColorChooser.showDialog(null, "Choose a Color for Player 2", view.p2Color);
				view.updateBoard(model.getBoard());
			}
		});	
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
	private boolean placePiece(final int x, final int y) {
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
			if (x == FLAG) {
				model.changeTurn();
				return;
			}
			if (x == 100) {
				System.out.println("Please enter a number between 1 and 8");
				x = -1;
			}
		}
		x--;

		while (y == -1) {
			System.out.print(model.getPlayer() + " Input y value");
			input = s.next();
			y = checkInput(input);
			if (y == FLAG) {
				model.changeTurn();
				return;
			}
			if (y == 100) {
				System.out.println("Please enter a number between 1 and 8");
				y = -1;
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
			return FLAG;
		} else if (input.toLowerCase().equals("pvp")) {
			pvp = true;
			model = new Model();
			return FLAG;
		} else if (input.toLowerCase().equals("pve")) {
			model = new Model();
			pvp = false;
			model.print();
			getInput();
			model.changeTurn();	
			return FLAG;
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
		return 100;
	}

	public void newGame() {
		model = new Model();
	}


	/**
	 * This method checks for a game over.
	 * If it is not game over, the turn switches to the next player.
	 * @return This returns the next player
	 */
	public final Piece nextTurn() { //This was for the text based version and will need to be removed before release
		model.print();
		getInput();
		model.changeTurn();	
		if (!pvp) {
			model.print();
			model.placePiece(model.bestMove()[2], model.bestMove()[1]);
			model.changeTurn();
		}
		return model.getPlayer();
	}

	public void nextTurnGUI(int col, int row){
		if (placePiece(col, row)) { //y,x
			model.changeTurn();
			if (!pvp) { //this isn't fully functional
				int[] count = model.countPieces();
				view.updateBoard(model.getBoard());
				view.updateCurrentPlayer(model.getPlayer(), count[1], count[2]);
				model.placePiece(model.bestMove()[2], model.bestMove()[1]);
				model.changeTurn();
				
				
			}
		} else { //If the move is not valid
			//System.out.println("Invalid move.");
			view.sendAlert("Invalid move.");
		}
		

	}
}
