package edu.gvsu.scis.cis350;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;



/**
 * This class handles the GUI.
 * @author Brendan Dent, Casey Zimmerman, Caitlin Crowe
 *
 * TODOs: When a player runs out of moves, a "game over" occurs even if the other player still has valid moves.
 *        Add functionality to "Save", "Load", "Customize"(allow player to customize the board colors) and "Rules"
 *        "Player vs Computer"/"Player vs Player" is only partially functional 
 *        		-Look in the presenter in the method nextTurnGUI() to see how the AI method is being called
 */
public class View {
	JFrame frame;

	JPanel winsPanel;
	JPanel playerPanel;
	private JPanel reversiBoard;
	
	private JButton[][] reversiBoardSquares = new JButton[BSIZE][BSIZE];

	JLabel bWins;
	JLabel wWins;
	JLabel currentPlayer;

	JMenuBar menus;
	JMenu fileMenu, gameMenu, helpMenu;
	JMenuItem quitItem, newGameItem, helpItem, saveItem, loadItem, customItem, pvpItem, pvcItem;


	private static final int BSIZE = 8;
	int b;
	int w;
	
	Color background = Color.GREEN, p1Color = Color.BLACK, p2Color = Color.WHITE;
	
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
		frame.setPreferredSize(new Dimension(600, 315));
		frame.setTitle("Reversi");

		reversiBoard = new JPanel(new GridLayout(0,9));
		reversiBoard.setBorder(new LineBorder(Color.BLACK));
		frame.add(BorderLayout.NORTH,reversiBoard);

		Insets buttonMargin = new Insets(0,0,0,0);
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				JButton butt = new JButton(" ");
				butt.setMargin(buttonMargin);
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

		currentPlayer = new JLabel("Current player: Player 1" +"                                "
				+"Player 1 Piece Count: " +bPiece+"         "+"Player 2 Piece Count: " +wPiece);

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
				
		fileMenu.add(newGameItem);
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		fileMenu.add(quitItem);
		gameMenu.add(customItem);		
		gameMenu.add(pvpItem);
		gameMenu.add(pvcItem);
		helpMenu.add(helpItem);
				
		menus = new JMenuBar();
		frame.setJMenuBar(menus);
		menus.add(fileMenu);
		menus.add(gameMenu);
		menus.add(helpMenu);

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
	
	public void addPVPActionListener(ActionListener a) {
		pvpItem.addActionListener(a);
	}
	
	public void addPVCActionListener(ActionListener a) {
		pvcItem.addActionListener(a);
	}
	
	public void addSaveActionListener(ActionListener a) {
		saveItem.addActionListener(a);
	}
	
	public void addLoadActionListener(ActionListener a) {
		loadItem.addActionListener(a);
	}
	public void addCustomActionListener(ActionListener a) {
		customItem.addActionListener(a);
	}
	

	public void updateBoard(Piece[][] gameBoard){
		for (int row = 0; row < BSIZE; row++) {
			for (int col = 0; col < BSIZE; col++) {
				if (gameBoard[row][col] == Piece.BLACK) { //if the piece at that position is black, make black
					reversiBoardSquares[row][col].setBackground(p1Color);
				} else if (gameBoard[row][col] == Piece.WHITE) { //if the piece at that position is white, make white
					reversiBoardSquares[row][col].setBackground(p2Color);
				} else {
					reversiBoardSquares[row][col].setBackground(background);
				}
			}
	    
		}
	}

	public void updateWinsPanel(int black, int white, boolean ties){
		//update the current score after a game ends
		b += black;
		w += white;
		bWins.setText("Player 1 score: " + b);
		wWins.setText("Player 2 score: " + w);
		System.out.println("B: "+b+" W: "+w);
		
		if (black == 1){
			JOptionPane.showMessageDialog(null,"Player 1 wins!");
		}
		else if (white == 1){
			JOptionPane.showMessageDialog(null,"Player 2 wins!");
		}
		else {
			JOptionPane.showMessageDialog(null,"There was a tie.");
		}
	}
	
	public void sendAlert(String s) {
		JOptionPane.showMessageDialog(null, s);
	}
	
	public void updateCurrentPlayer(Piece obj, int bPiece, int wPiece){
		if (obj == Piece.BLACK) {
			currentPlayer.setText("Current player: Player 1" +"                                "
					+"Player 1 Piece Count: " +bPiece+"         "+"Player 2 Piece Count: " +wPiece);
		} else if (obj == Piece.WHITE) {
			currentPlayer.setText("Current player: Player 2" +"                                "
					+"Player 1 Piece Count: " +bPiece+"         "+"Player 2 Piece Count: " +wPiece);
		} else {
			currentPlayer.setText("Error");
		}
		
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