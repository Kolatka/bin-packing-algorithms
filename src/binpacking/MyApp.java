package binpacking;

import gui.MainWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MyApp extends Application {
	private Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainWindowController mwc = new MainWindowController();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/MainWindow.fxml"));
		
		mwc.showStage(root, primaryStage);

		primaryStage.setTitle("Problem pakowania");
		primaryStage.setOnCloseRequest(e->{
			e.consume();	
			primaryStage.close();
			Platform.exit();
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
