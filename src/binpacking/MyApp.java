package binpacking;

import gui.MainWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * @author Kolatka
 *
 */
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
		
		
		/*
		long startTime = System.currentTimeMillis();
		DataManager dm = new DataManager();
		ArrayList<Item> items = dm.generateItems();
		dm.setItems(items);

		dm.printItems();
		NextFit nf = new NextFit(items);
		FirstFit ff = new FirstFit(items);
		DataManager dmnf = nf.solve();
		DataManager dmff = ff.solve();

		System.out.println("\t Data");
		System.out.println("Number of items: \t" + dm.getItems().size());
		System.out.println("Weight of items: \t" + dm.getTotalWeight());
		System.out.println("Container volume: \t" + dm.getContainerVolume());
		System.out.println("============================");
		System.out.println("\t Results");
		System.out.println("Lower Bound: \t\t" + dm.getLowerBound());
		System.out.println("Next fit algorithm: \t" + dmnf.getResult());
		System.out.println("First fit algorithm: \t" + dmff.getResult());

		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    //System.out.println("\nTime: " + (double)elapsedTime/1000);
	
	    //dmff.printContainers();
	    
	   
	    
	    	BranchAndBound bab = new BranchAndBound(items);

	    	DataManager dmbab = bab.solve();

		    System.out.println("bab algorithm: \t" + bab.optcost);
		    System.out.println("------------");
		    for(int i=0;i<bab.optlist.size();i++){
		    	//System.out.println(bab.optlist.get(i) + " - " + dm.getItems().get(i).getWeight());
		    }

	    //dm.printItems();
	    
	    //dmbab.printContainers();
	    System.out.println("finished");
	    
	    */
	}
}
