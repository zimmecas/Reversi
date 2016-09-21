
public class View {
	Presenter presenter;
	
	public View(){
		initComponents();
	}
	
	private void initComponents(){
		/*
		 * assigns everything in the GUI and sets action listeners
		 */
		
		
	}
	
	//public boolean placePieceListener(int x, int y){
	//	return false;
	//}
	
	public Presenter getPresenter(){
		return presenter;
	}
	
	public void setPresenter(Presenter p){
		presenter = p;
	}
}
