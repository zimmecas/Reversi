import java.util.Scanner;

/**
 * 
 * @author Caitlin Crowe
 *
 */
public class Presenter {
	
	/**
	 * 
	 */
	private Model model;
	
	/**
	 * 
	 */
	private View view;
	
	/**
	 * 
	 */
	private Scanner s = new Scanner(System.in);
	/**
	 * The Presenter constructor.
	 * @param m is the initial value of model.
	 * @param v is the initial value of view.
	 */
	public Presenter(final Model m, final View v) {
		model = m;
		view = v;
	}
	
	/**
	 * This method returns the view.
	 * @return view
	 */
	public final View getView() {
		return view;
	}
	
	/**
	 * This method sets the view.
	 * @param v is what view is set to.
	 */
	public final void setView(final View v) {
		view = v;
	}
	
	/**
	 * This method returns the model.
	 * @return model
	 */
	public final Model getModel() {
		return model;
	}
	
	/**
	 * This method sets the model.
	 * @param m is what model is set to.
	 */
	public final void setModel(final Model m) {
		model = m;
	}
	
	/**
	 * This method places a piece.
	 * @param x is the x position of where to place the piece.
	 * @param y is the y position of where to place the piece.
	 * @return model.placePiece(x, y)
	 */
	public final boolean placePiece(/*final int x, final int y*/) {
		int x = s.nextInt();
		int y = s.nextInt();
		return model.placePiece(x, y);
	}
}
