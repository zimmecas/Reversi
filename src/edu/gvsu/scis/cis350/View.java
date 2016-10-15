package edu.gvsu.scis.cis350;

/**
 * This class handles the GUI.
 * @author Brendan Dent, Casey Zimmerman, Caitlin Crowe
 *
 */
public class View {
	
	/**
	 * This variable is used to communicate with the Presenter class.
	 */
	private Presenter presenter;

	public View() {
		initComponents();
	}

	private void initComponents() {
		/*
		 * assigns everything in the GUI and sets action listeners
		 */
	}

	//public boolean placePieceListener(int x, int y){
	//	return false;
	//}

	public final Presenter getPresenter() {
		return presenter;
	}

	public final void setPresenter(final Presenter p) {
		presenter = p;
	}
}
