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

	/**
	 * This is the constructor of View and calls initComponents to run the GUI.
	 */
	public View() {
		initComponents();
	}

	/**
	 * This method assigns everything in the GUI and sets action listeners.
	 */
	private void initComponents() {
		/*
		 * For release 2.
		 */
	}

	//public boolean placePieceListener(int x, int y){
	//	return false;
	//}

	/**
	 * This method returns the value of presenter.
	 * @return presenter This is the current value of presenter
	 */
	public final Presenter getPresenter() {
		return presenter;
	}

	/**
	 * This method sets presenter equal to a new value.
	 * @param p This is the value that presenter will be set equal to
	 */
	public final void setPresenter(final Presenter p) {
		presenter = p;
	}
}
