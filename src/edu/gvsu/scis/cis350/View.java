package edu.gvsu.scis.cis350;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


/**
 * This class handles the GUI.
 * @author Brendan Dent, Casey Zimmerman, Caitlin Crowe
 *
 * TODOs: Add "Rules"
 *        Add a delay before the computer's moves, if possible
 */
public class View {
	/**
	 * The GUI frame.
	 */
	private JFrame frame;

	/**
	 * The panel that keeps track of the players' wins.
	 */
	private JPanel winsPanel;
	/**
	 * The panel that keeps track of the current player and the piece counts.
	 */
	private JPanel playerPanel;
	/**
	 * This panel contains the board.
	 */
	private JPanel reversiBoard;
	
	/**
	 * A 2D array of JButtons that make up the board.
	 */
	private JButton[][] boardSquares = new JButton[BSIZE][BSIZE];

	/**
	 * This label displays player 1's (black) wins.
	 */
	private JLabel bWins;
	/**
	 * This label displays player 2's (white) wins.
	 */
	private JLabel wWins;
	/**
	 * This label displays the current player.
	 */
	private JLabel currentPlayer;

	/**
	 * This variable is for the menu bar.
	 */
	private JMenuBar menus;
	/**
	 * These are the menus that will go into the menu bar.
	 */
	private JMenu fileMenu, gameMenu, helpMenu;

	/**
	 * When this item is clicked, it will close the game.
	 */
	private JMenuItem quitItem;
	/**
	 * When this item is clicked, it will start a new game.
	 */
	private JMenuItem newGameItem;
	/**
	 * When this item is clicked, it will show the game rules.
	 */
	private JMenuItem helpItem;
	/**
	 * When this item is clicked, it will save the game file.
	 */
	private JMenuItem saveItem;
	/**
	 * When this item is clicked, the player can choose a game file to load.
	 */
	private JMenuItem loadItem;
	/**
	 * When this item is clicked, the player can change the colors of the board.
	 */
	private JMenuItem customItem;
	/**
	 * When this item is clicked, the game will be player vs player.
	 */
	private JMenuItem pvpItem;
	/**
	 * When this item is clicked, the game will be player vs computer.
	 */
	private JMenuItem pvcItem;
	/**
	 * When this item is clicked, the player's valid moves will be shown.
	 */
	private JMenuItem validMovesItem;
	/**
	 * When this item is clicked, a recommended move will be shown.
	 */
	private JMenuItem recommendItem;

	/**
	 * This is the length of the board.
	 */
	private static final int BSIZE = 8;
	/**
	 * A flag for when an error occurs.
	 */
	private static final int FLAG = -2;
	/**
	 * The width of the frame.
	 */
	private static final int DIMX = 600;
	/**
	 * The height of the frame.
	 */
	private static final int DIMY = 315;
	/**
	 * This keeps track of player 1's (black score.
	 */
	private int b;
	/**
	 * This keeps track of player 2's (white) score.
	 */
	private int w;

	/**
	 * This is the default background color of the board.
	 */
	private Color background = Color.GREEN;
	/**
	 * This is the default color of player 1's pieces.
	 */
	private Color p1Color = Color.BLACK;
	/**
	 * This is the default color of player 2's pieces.
	 */
	private Color p2Color = Color.WHITE;
	/**
	 * This is the color of valid moves and the recommended move.
	 */
	private Color helpColor = Color.YELLOW;
	
	/**
	 * This is used for numbering the board.
	 */
	private static final String COLS = "12345678";

	/**
	 * This is the constructor of View and assigns everything in the GUI.
	 */
	public View() {
		b = 0;
		w = 0;
		int bPiece = 2;
		int wPiece = 2;

		// establish the frame
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(DIMX, DIMY));
		frame.setTitle("Reversi");

		reversiBoard = new JPanel(new GridLayout(0, BSIZE + 1));
		reversiBoard.setBorder(new LineBorder(Color.BLACK));
		frame.add(BorderLayout.NORTH, reversiBoard);

		Insets buttonMargin = new Insets(0, 0, 0, 0);
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				JButton butt = new JButton(" ");
				butt.setMargin(buttonMargin);
				butt.setBackground(Color.GREEN);
				butt.setAlignmentX(col);
				butt.setAlignmentY(row);

				boardSquares[col][row] = butt;
			}
		}

		reversiBoard.add(new JLabel(""));
		for (int i = 0; i < BSIZE; i++) {
			reversiBoard.add(new JLabel(
					COLS.substring(i, i + 1), SwingConstants.CENTER));
		}

		for (int i = 0; i < BSIZE; i++) {
			for (int j = 0; j < BSIZE; j++) {
				switch (j) {
				case 0:
					reversiBoard.add(new JLabel(
							"" + (i + 1), SwingConstants.CENTER));
				default:
					reversiBoard.add(boardSquares[j][i]);
				}
			}
		}

		currentPlayer = new JLabel("Current player: Player 1" 
				+ "                             " + "Player 1 Piece Count: " 
				+ bPiece + "         " + "Player 2 Piece Count: " + wPiece);

		//panel that shows the current player
		playerPanel = new JPanel();
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
		playerPanel.add(currentPlayer);
		frame.add(BorderLayout.CENTER, playerPanel); 


		//This is for a panel to keep track of the wins. 
		//This was taken directly from a past project so it most likely
		//needs some changes, but that can be taken care of later.
		bWins = new JLabel("Player 1 score: " + b);
		wWins = new JLabel("Player 2 score: " + w);

		// create wins panel
		winsPanel = new JPanel();
		winsPanel.setLayout(new BoxLayout(winsPanel, BoxLayout.Y_AXIS));
		winsPanel.add(bWins);
		winsPanel.add(wWins);
		frame.add(BorderLayout.SOUTH, winsPanel); 

		// set up File menu
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		gameMenu = new JMenu("Game");
		
		helpItem = new JMenuItem("Rules");
		saveItem = new JMenuItem("Save");
		loadItem = new JMenuItem("Load");
		quitItem = new JMenuItem("Quit");
		customItem = new JMenuItem("Customize");
		newGameItem = new JMenuItem("New Game");
		pvpItem = new JMenuItem("Player vs Player");
		pvcItem = new JMenuItem("Player vs Computer");
		validMovesItem = new JMenuItem("Show Valid Moves");
		recommendItem = new JMenuItem("Show Recommended Move");
				
		fileMenu.add(newGameItem);
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		fileMenu.add(quitItem);
		gameMenu.add(customItem);		
		gameMenu.add(pvpItem);
		gameMenu.add(pvcItem);
		helpMenu.add(helpItem);
		helpMenu.add(validMovesItem);
		helpMenu.add(recommendItem);
				
		menus = new JMenuBar();
		frame.setJMenuBar(menus);
		menus.add(fileMenu);
		menus.add(gameMenu);
		menus.add(helpMenu);

		frame.setVisible(true);
		frame.pack();
	}

	/**
	 * This adds the ActionListeners for the board buttons in the presenter.
	 * @param a This is the ActionListener that will be sent to the presenter.
	 */
	public final void addBoardActionListeners(final ActionListener a) {
		for (int i = 0; i < BSIZE; i++) {
			for (int j = 0; j < BSIZE; j++) {
				boardSquares[j][i].addActionListener(a);
			}
		}
	}
	/**
	 * This adds the ActionListeners for Quit in the presenter.
	 * @param a This is the ActionListener that will be sent to the presenter.
	 */
	public final void addQuitActionListener(final ActionListener a) {
		quitItem.addActionListener(a);
	}
	/**
	 * This adds the ActionListeners for New Game in the presenter.
	 * @param a This is the ActionListener that will be sent to the presenter.
	 */
	public final void addNewGameActionListener(final ActionListener a) {
		newGameItem.addActionListener(a);
	}
	/**
	 * This adds the ActionListeners for Player vs Player in the presenter.
	 * @param a This is the ActionListener that will be sent to the presenter.
	 */
	public final void addPVPActionListener(final ActionListener a) {
		pvpItem.addActionListener(a);
	}
	/**
	 * This adds the ActionListeners for Player vs Computer in the presenter.
	 * @param a This is the ActionListener that will be sent to the presenter.
	 */	
	public final void addPVCActionListener(final ActionListener a) {
		pvcItem.addActionListener(a);
	}
	/**
	 * This adds the ActionListeners for Save in the presenter.
	 * @param a This is the ActionListener that will be sent to the presenter.
	 */
	public final void addSaveActionListener(final ActionListener a) {
		saveItem.addActionListener(a);
	}
	/**
	 * This adds the ActionListeners for Load in the presenter.
	 * @param a This is the ActionListener that will be sent to the presenter.
	 */	
	public final void addLoadActionListener(final ActionListener a) {
		loadItem.addActionListener(a);
	}
	/**
	 * This adds the ActionListeners for Customize in the presenter.
	 * @param a This is the ActionListener that will be sent to the presenter.
	 */
	public final void addCustomActionListener(final ActionListener a) {
		customItem.addActionListener(a);
	}
	/**
	 * This adds the ActionListeners for Valid Moves in the presenter.
	 * @param a This is the ActionListener that will be sent to the presenter.
	 */
	public final void addValidMovesActionListener(final ActionListener a) {
		validMovesItem.addActionListener(a);
	}
	/**
	 * This adds the ActionListeners for Recommended Moves in the presenter.
	 * @param a This is the ActionListener that will be sent to the presenter.
	 */
	public final void addRecommendActionListener(final ActionListener a) {
		recommendItem.addActionListener(a);
	}
	/**
	 * This adds the ActionListeners for Rules in the presenter.
	 * @param a This is the ActionListener that will be sent to the presenter.
	 */
	public final void addHelpActionListener(final ActionListener a) {
		helpItem.addActionListener(a);
	}

	/**
	 * This method updates the board colors based on the updated piece values.
	 * @param gameBoard This board contains the updated piece values. 
	 */
	public final void updateBoard(final Piece[][] gameBoard) {
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				if (gameBoard[row][col] == Piece.BLACK) {
					boardSquares[row][col].setBackground(p1Color);
				} else if (gameBoard[row][col] == Piece.WHITE) {
					boardSquares[row][col].setBackground(p2Color);
				} else {
					boardSquares[row][col].setBackground(background);
				}
			}
	    
		}
	}
	
	/**
	 * This method displays the current player's valid moves.
	 * @param row The row of a valid move
	 * @param col The column of a valid move
	 */
	public final void showValidMoves(final int row, final int col) {
		boardSquares[row][col].setBackground(helpColor);
	}

	/**
	 * This updates the winsPanel and displays who has won the game.
	 * @param black If player 1 has won, black = 1
	 * @param white If player 2 has won, white = 1
	 * @param ties If true, a tie has occurred 
	 */
	public final void updateWinsPanel(final int black, 
			final int white, final boolean ties) {
		//update the current score after a game ends
		b += black;
		w += white;
		bWins.setText("Player 1 score: " + b);
		wWins.setText("Player 2 score: " + w);
		
		if (black == 1) {
			JOptionPane.showMessageDialog(null, "Player 1 wins!");
		} else if (white == 1) {
			JOptionPane.showMessageDialog(null, "Player 2 wins!");
		} else if (ties) {
			JOptionPane.showMessageDialog(null, "There was a tie.");
		} else if ((black == 0) && (white == 0) && !ties) {
			System.out.println("Game loaded.");
			//do nothing- this updates the scores when a game file is loaded.
		} else {
			sendAlert("Error");
		}
	}
	/**
	 * This shows a pop-up to alert the player of a problem.
	 * @param s This is the message that should be displayed to the player
	 */
	public final void sendAlert(final String s) {
		JOptionPane.showMessageDialog(null, s);
	}
	/**
	 * This updates the current player and the pieces counts after every turn.
	 * @param obj The piece of the current player
	 * @param bPiece The piece count for player 1
	 * @param wPiece The piece count for player 2
	 */
	public final void updateCurrentPlayer(final Piece obj,
			final int bPiece, final int wPiece) {
		if (obj == Piece.BLACK) {
			currentPlayer.setText("Current player: Player 1" 
					+ "                             " + "Player 1 Piece Count: "
					+ bPiece + "         " + "Player 2 Piece Count: " + wPiece);
		} else if (obj == Piece.WHITE) {
			currentPlayer.setText("Current player: Player 2" 
					+ "                             " + "Player 1 Piece Count: "
					+ bPiece + "         " + "Player 2 Piece Count: " + wPiece);
		} else {
			currentPlayer.setText("Error");
		}
		
	}

	/**
	 * This method send the presenter the row of the selected button.
	 * @param event The event triggered by the selected button
	 * @return This is the row of the selected button
	 */
	public final int getButtonRow(final Object event) {
		for (int r = 0; r < BSIZE; r++) {
			for (int c = 0; c < BSIZE; c++) {
				if (boardSquares[r][c] == event) { //event is e.getSource()
					return r;
				}
			}
		}
		return FLAG;
	}
	/**
	 * This method send the presenter the column of the selected button.
	 * @param event The event triggered by the selected button
	 * @return This is the column of the selected button
	 */
	public final int getButtonCol(final Object event) {
		for (int r = 0; r < BSIZE; r++) {
			for (int c = 0; c < BSIZE; c++) {
				if (boardSquares[r][c] == event) { //event is e.getSource()
					return c;
				}
			}
		}
		return FLAG;
	}

	/**
	 * This returns the frame so the presenter can use it.
	 * @return This is the frame of the GUI
	 */
	public final JFrame getFrame() {
		return frame;
	}
	/**
	 * This gets the value of b for the presenter to use.
	 * @return This is the value of b
	 */
	public final int getB() {
		return b;
	}
	/**
	 * This sets b to a new value.
	 * @param i The new value to set b to
	 */
	public final void setB(final int i) {
		b = i;
	}
	/**
	 * This gets the value of w for the presenter to use.
	 * @return This is the value of w
	 */	
	public final int getW() {
		return w;
	}
	/**
	 * This sets b to a new value.
	 * @param i The new value to set b to
	 */
	public final void setW(final int i) {
		w = i;
	}

	/**
	 * This gets the background color.
	 * @return The value of the background color
	 */
	public final Color getBackground() { 
		return background;
	}
	/**
	 * This sets the background color.
	 * @param c The new value of the background color
	 */
	public final void setBackground(final Color c) {
		background = c;
	}
	/**
	 * This gets player 1's color.
	 * @return The value of player 1's color
	 */
	public final Color getP1Color() {
		return p1Color;
	}
	/**
	 * This sets player 1's color.
	 * @param c The new value of player 1's color
	 */
	public final void setP1Color(final Color c) {
		p1Color = c;
	}
	/**
	 * This gets player 2's color.
	 * @return The value of player 2's color
	 */
	public final Color getP2Color() {
		return p2Color;
	}
	/**
	 * This sets player 2's color.
	 * @param c The new value of player 2's color
	 */
	public final void setP2Color(final Color c) {
		p2Color = c;
	}
	/**
	 * This gets color of the help functions.
	 * @return The color of the help functions
	 */
	public final Color getHelpColor() {
		return helpColor;
	}
	/**
	 * This sets the color of the help functions to a new value.
	 * @param c The new color for the help functions
	 */
	public final void setHelpColor(final Color c) {
		helpColor = c;
	}
}