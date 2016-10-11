
public class Main {
	public static void main(final String[] args) {
		View view = new View();
		Model model = new Model();
		model.print();
		new Presenter(model, view);
		
	}
}
