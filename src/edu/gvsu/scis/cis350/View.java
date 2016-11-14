package edu.gvsu.scis.cis350;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



/**
 * This class handles the GUI.
 * @author Brendan Dent, Casey Zimmerman, Caitlin Crowe
 *
 * TODOs: Should have something that says who the current player is.
 * 		  Should have something that says how many pieces each player has.
 * 		  Handle GameOver and updating the winsPanel accordingly.
 *        NewGame is not working yet.
 *        Add color changing.
 */
public class View {
	JFrame frame;

	JPanel winsPanel;
	JPanel buttonPanel;

	JLabel bWins;
	JLabel wWins;

	JMenuBar menus;
	JMenu fileMenu;
	JMenuItem quitItem, newGameItem;


	private static final int BSIZE = 8;
	private int b;
	private int w;

	private JButton[][] board;


	/**
	 * This is the constructor of View and assigns everything in the GUI.
	 */
	public View() {
		b = 0;
		w = 0;

		board = new JButton[BSIZE][BSIZE];

		// establish the frame
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(600, 315));
		frame.setTitle("Reversi");

		// create button panel
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				board[row][col] = new JButton(" ");
				board[row][col].setBackground(Color.GREEN);
			}
		}

		buttonPanel = new JPanel(new GridLayout(BSIZE, BSIZE));
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				buttonPanel.add(board[row][col]);
			}
		}
		frame.add(BorderLayout.SOUTH, buttonPanel);

		//This is for a panel to keep track of the wins. 
		//This was taken directly from a past project so it most likely
		//needs some changes, but that can be taken care of later.
		bWins = new JLabel("Black score: " + b);
		wWins = new JLabel("White score: " + w);

		// create wins panel
		winsPanel = new JPanel();
		winsPanel.setLayout(new BoxLayout(winsPanel, BoxLayout.Y_AXIS));
		winsPanel.add(bWins);
		winsPanel.add(wWins);
		frame.add(BorderLayout.NORTH, winsPanel); 

		// set up File menu
		fileMenu = new JMenu("File");
		quitItem = new JMenuItem("Quit");
		newGameItem = new JMenuItem("New Game");
		fileMenu.add(newGameItem);
		fileMenu.add(quitItem);
		menus = new JMenuBar();
		frame.setJMenuBar(menus);
		menus.add(fileMenu);

		frame.setVisible(true);
		frame.pack();
	}

	public void addBoardActionListeners(ActionListener a) {
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				board[row][col].addActionListener(a);
			}
		} 		
	}

	public void addQuitActionListener(ActionListener a){
		quitItem.addActionListener(a);
	}

	public void addNewGameActionListener(ActionListener a){
		newGameItem.addActionListener(a);
	}

	public void updateBoard(Piece[][] gameBoard){
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				if (gameBoard[row][col] == Piece.BLACK) { //if the piece at that position is black, make black
					board[row][col] = new JButton("B");
					board[row][col].setBackground(Color.BLACK);
				} else if (gameBoard[row][col] == Piece.WHITE) { //if the piece at that position is white, make white
					board[row][col] = new JButton("W");
					board[row][col].setBackground(Color.WHITE);
				} else {
					board[row][col] = new JButton(" ");
					board[row][col].setBackground(Color.GREEN);
				}
			}
		}

	}

	public void updateWinsPanel(){
		//update the current score after a game ends
	}
}

	/*
	 * A huge part of the old GUI. Keeping this for reference for now,
	 * but it needs to be deleted eventually.
	public void updatePanel() {	
		//reset and update button panel
		board = new JButton[BSIZE][BSIZE];

		// create button panel
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				board[row][col] = new JButton(" ");
			}
		}

		buttonPanel = new JPanel(new GridLayout(BSIZE, BSIZE));
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				buttonPanel.add(board[row][col]);
			}
		}

		// register the listeners 
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				board[row][col].addActionListener(listener);
			}
		}

		//reset winsPanel
		b = 0;
		w = 0;        

		winsPanel.remove(bWins);
		winsPanel.remove(wWins);
		bWins = new JLabel("Black score: "+b);
		wWins = new JLabel("White score: "+w);
		winsPanel.add(bWins);
		winsPanel.add(wWins);
	}


	private class ButtonListener implements ActionListener {
		private static final int BSIZE = 8;
		public void actionPerformed(ActionEvent e) {
			Object comp = e.getSource();

			if (comp == newGameItem) {
				//game = presenter.newGame(); //create new game- this gives NullPointer

				frame.remove(buttonPanel); //remove the old game information 
				frame.remove(winsPanel);
				updatePanel(); //update with new game information
				frame.add(BorderLayout.SOUTH, buttonPanel); 
				frame.add(BorderLayout.NORTH, winsPanel);

				frame.revalidate();
				frame.repaint();				
			}
			else if (comp == quitItem) { 
				System.exit(0);
			}
			//this is a mess and does not work right now
			else{ //board buttons
				for(int row = 0; row < BSIZE; row++) //beginning of first chunk of code given to us
					for (int col = 0; col < BSIZE; col++) {
						if (board[row][col] == comp) { //comp is e.getSource()
							if (game.placePiece(row, col) == true){ //if at the chosen position, and the spot is not taken
								if(game.getPlayer() == Piece.WHITE){
									board[row][col].setText("W");
									board[row][col].setBackground(Color.WHITE);
									//update board[row][col] to be Piece.WHITE-- This might actually be happening, but the board is not updating correctly
								}
								else if(game.getPlayer() == Piece.BLACK){
									board[row][col].setText("B");
									board[row][col].setBackground(Color.BLACK);
									//update board[row][col] to be Piece.BLACK
								}
								game.changeTurn(); 
							}
							else{
								JOptionPane.showMessageDialog(null, "Invalid move.");

							}
						}
					}	
				//Here: If the game has been completed, check to see who the winner is
				//if winner is black- +1 to black's score in winsPanel
				//if winner is white- +1 to white's score in winsPanel
				//if there is no winner, don't update any score
			}			
		}
	}	*/





