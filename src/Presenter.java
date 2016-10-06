
public class Presenter {
	/*
	 * 
	 */
	
	Model model;
	View view;
	
	public Presenter(Model m, View v){
		model = m;
		view = v;
	}
	
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
	
	public boolean placePiece(int x, int y){
		return model.placePiece(x, y);
	}
	
	
}
