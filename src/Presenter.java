
public class Presenter {
	/*
	 * 
	 */
	
	Model model;
	View view;
	
	public View getView(){
		return view;
	}
	
	public void setView(View v){
		view = v;
	}
	
	public Model getModel(){
		return model;
	}
	
	public void setModel(Model m){
		model = m;
	}
	
	public boolean placePiece(int x, int y, Piece p){
		return model.placePiece(x, y, p);
	}
	
	
}
