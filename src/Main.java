
public class Main {
	static void main(final String[] args) {
		View view = new View();
		Model model = new Model();
		new Presenter(model, view);
	}
}
