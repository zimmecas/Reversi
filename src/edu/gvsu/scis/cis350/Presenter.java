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
				view.updateCurrentPlayer(model.getPlayer(), count[1], count[2]);
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
					view.updateWinsPanel(0, 0, false);
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
				view.helpColor = JColorChooser.showDialog(null, "Choose a Color for Showing Moves", view.helpColor);
				view.updateBoard(model.getBoard());
			}
		});	
		
		view.addValidMovesActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				for(int i = 0; i < model.getBoardSize(); i++)
					for(int j = 0; j < model.getBoardSize(); j++)
						if(model.isValidMove(j, i)){
						
							view.showValidMoves(i, j);
							}
			}
		});
		
		view.addRecommendActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				view.showValidMoves(model.bestMove()[1], model.bestMove()[2]);
			}
		});
		
		view.addHelpActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "The Rules\n\nReversi takes place between two players, black and white, on an 8x8 board of 64 squares."
+"\nThe board is set up initially with two black discs and two white discs in the center."
+"\nBlack always plays first with players then taking alternate turns."
+"\nAt each turn a player must place a disc with their colour face up on one of the empty squares of the board, adjacent to an opponent's disc "
+"\nsuch that one or more straight lines (horizontal, vertical or diagonal) are formed from the newly placed disc,"
+"\nthrough one or more of the opponent's discs and up to other discs of their own colour already on the board. "
+"\nAll the intervening discs of the opponent's colour are flipped to the colour of the newly laid disc."
+"\nDiscs may be flipped from one colour to the other but once played are not moved from one square to another or removed from the board."
+"\nPlayers may not pass unless there is no valid move available to them in which case they must pass."
+"\nPlay continues until neither player is able to move, usually when all 64 squares have been played."
+"\nThe winner is the player with most pieces turned to their colour at the close of play.");
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
	 * This method resets the game.
	 */


	public void newGame() {
		model = new Model();
	}

	void nextTurnGUI(int col, int row){
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
			view.sendAlert("Invalid move.");
		}
		

	}
}
