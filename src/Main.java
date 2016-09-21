
public class Main {
	public static void main(String[] args){
		Model model = new Model();
		Presenter presenter = new Presenter();
		presenter.setModel(model);
		View view = new View();
		presenter.setView(view);
		
	}
}
