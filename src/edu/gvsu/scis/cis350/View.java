package edu.gvsu.scis.cis350;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.LineBorder;



/**
 * This class handles the GUI.
 * @author Brendan Dent, Casey Zimmerman, Caitlin Crowe
 *
 * TODOs: Should have something that says who the current player is.
 * 		  Should have something that says how many pieces each player has.
 * 		  Handle GameOver and updating the winsPanel accordingly.
 *        NewGame is not working yet. --should be working but can't really test yet
 *        Add color changing.
 *        Ability to decide to play player vs player or player vs comp
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
	private JButton[][] reversiBoardSquares = new JButton[BSIZE][BSIZE];
	private JPanel reversiBoard;
	private static final String COLS = "12345678";

	/**
	 * This is the constructor of View and assigns everything in the GUI.
	 */
	public View() {
		b = 0;
		w = 0;

		// establish the frame
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(600, 315));
		frame.setTitle("Reversi");
		
		reversiBoard = new JPanel(new GridLayout(0,9));
		reversiBoard.setBorder(new LineBorder(Color.BLACK));
		frame.add(reversiBoard);

		Insets buttonMargin = new Insets(0,0,0,0);
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				JButton butt = new JButton(" ");
				butt.setMargin(buttonMargin);
				ImageIcon icon = new ImageIcon(new BufferedImage(64,64, BufferedImage.TYPE_INT_ARGB));
				butt.setIcon(icon);
				butt.setBackground(Color.GREEN);
				butt.setAlignmentX(col);
				butt.setAlignmentY(row);
				
				reversiBoardSquares[col][row] = butt;
			}
		}
		
		reversiBoard.add(new JLabel(""));
		
		for (int ii = 0; ii < BSIZE; ii++){
			reversiBoard.add(new JLabel(COLS.substring(ii, ii + 1), SwingConstants.CENTER));
		}
		
		for (int ii = 0; ii < BSIZE; ii++){
			for (int jj = 0; jj < BSIZE; jj++){
				switch (jj) {
					case 0:
						reversiBoard.add(new JLabel("" + (ii + 1), SwingConstants.CENTER));
					default:
						reversiBoard.add(reversiBoardSquares[jj][ii]);
				}
			}
		}
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
		frame.add(BorderLayout.SOUTH, winsPanel); 

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
		for (int ii = 0; ii < BSIZE; ii++){
			for (int jj = 0; jj < BSIZE; jj++){
				reversiBoardSquares[jj][ii].addActionListener(a);
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
					reversiBoardSquares[row][col].setBackground(Color.BLACK);
				} else if (gameBoard[row][col] == Piece.WHITE) { //if the piece at that position is white, make white
					reversiBoardSquares[row][col].setBackground(Color.WHITE);
				} else {
					reversiBoardSquares[row][col].setBackground(Color.GREEN);
				}
			}
		}

	}

	public void updateWinsPanel(){
		//update the current score after a game ends
	}
	
	public int getButtonRow(Object event){
		for(int r = 0; r < BSIZE; r++) {
			for (int c = 0; c < BSIZE; c++) {
				if (reversiBoardSquares[r][c] == event) { //event is e.getSource()
					return r;
				}
			}
		}
		return -2;
	}
	
	public int getButtonCol(Object event){
		for(int r = 0; r < BSIZE; r++) {
			for (int c = 0; c < BSIZE; c++) {
				if (reversiBoardSquares[r][c] == event) { //event is e.getSource()
					return c;
				}
			}
		}
		return -2;
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





