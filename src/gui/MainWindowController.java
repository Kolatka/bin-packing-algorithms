package gui;

import java.net.URL;
import java.util.ResourceBundle;

import binpacking.BranchAndBound;
import binpacking.BruteForce;
import binpacking.DataManager;
import binpacking.FirstFit;
import binpacking.NextFit;
import binpacking.Parameters;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindowController implements Initializable {
	@FXML 
	private BorderPane mainPane;
	@FXML
    private Button generateButton;
	@FXML
    private Button startButton;
	@FXML
    private Button stopButton;
	@FXML
    private Button containerButton;
	@FXML
	private TextField minWeightField;
	@FXML
	private TextField maxWeightField;
	@FXML
	private TextField itemsNumberField;
	@FXML
	private TextField containerVolumeField;
	@FXML
	private CheckBox nfRandom;
	@FXML
	private CheckBox ffRandom;
	@FXML
	private CheckBox bfRandom;
	@FXML
	private CheckBox babRandom;
	@FXML
	private CheckBox nfInc;
	@FXML
	private CheckBox ffInc;
	@FXML
	private CheckBox bfInc;
	@FXML
	private CheckBox babInc;
	@FXML
	private CheckBox nfDec;
	@FXML
	private CheckBox ffDec;
	@FXML
	private CheckBox bfDec;
	@FXML
	private CheckBox babDec;
	@FXML
	private TextArea itemsArea;
	@FXML
	private TextArea resultArea;
	
	Stage stage;
	DataManager mainData;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startButton.setDisable(true);
		stopButton.setDisable(true);
		containerButton.setDisable(true);
		setDefaultValues();
		Parameters.stop = false;
		itemsArea.setStyle("-fx-font-size: 11");
		resultArea.setStyle("-fx-font-size: 11");
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) {
		stage = (Stage) mainPane.getScene().getWindow();
	    Parent root;
		if(event.getSource()==generateButton){   
			itemsArea.clear();
			
			Parameters.minItemWeight = Integer.parseInt(minWeightField.getText());
			Parameters.maxItemWeight = Integer.parseInt(maxWeightField.getText());
			Parameters.itemsNumber = Integer.parseInt(itemsNumberField.getText()); 
			Parameters.containerVolume = Integer.parseInt(containerVolumeField.getText());
			Thread thread = new Thread(() -> {
				long startTime = System.currentTimeMillis();
				mainData = new DataManager();
				mainData.generateItems();
				if(Parameters.itemsNumber<10000)print(mainData.printItems(mainData.getItems()),itemsArea);
				else print("Zbyt wiele przedmiot�w �eby wy�wietli�",itemsArea);
				long stopTime = System.currentTimeMillis();
				print("\nWygenerowano w: " + (stopTime - startTime) + "ms\n",itemsArea);
				});
			thread.start();
			startButton.setDisable(false);
		}else if(event.getSource()==startButton){  
			Parameters.stop = false;
			stopButton.setDisable(false);
			startButton.setDisable(true);
			Thread thread2 = new Thread(() -> {
				resultArea.clear();
				print("Liczba przedmiot�w: " + mainData.getItems().size(),resultArea);
				print("Rozmiar przedmiot�w: "+ mainData.getTotalWeight(),resultArea);
				print("Rozmiar kontenera: "+ mainData.getContainerVolume(),resultArea);

				print("\nWyniki:\n",resultArea);
				print("Lower Bound: \t\t" + mainData.getLowerBound(),resultArea);
			
				solve();
				print("\nZakonczono",resultArea);
				startButton.setDisable(false);
				containerButton.setDisable(false);
				stopButton.setDisable(true);
			});
			thread2.start();

		}else if(event.getSource()==stopButton){  
			Parameters.stop = true;
			startButton.setDisable(false);
		}else if(event.getSource()==containerButton){  
			containerButton.setDisable(true);
			printContainers();
		}
		
	}
	//old bad coding, need to fix this asap
	DataManager dmnf;
	DataManager dmff;
	DataManager dmbf;
	DataManager dmbab;
	DataManager dmnfInc;
	DataManager dmffInc;
	DataManager dmbfInc;
	DataManager dmbabInc;
	DataManager dmnfDec;
	DataManager dmffDec;
	DataManager dmbfDec;
	DataManager dmbabDec;
	private void solve(){
		if(nfRandom.isSelected() || ffRandom.isSelected() || bfRandom.isSelected() || babRandom.isSelected()) print("\nLosowo: ",resultArea);
		if(nfRandom.isSelected()){
			long startTime = System.currentTimeMillis();
			dmnf = new NextFit(mainData.getItems()).solve();
			long stopTime = System.currentTimeMillis();
			print("Next fit algorithm: \t\t" + dmnf.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
		}
		if(ffRandom.isSelected()){
			long startTime = System.currentTimeMillis();
			dmff = new FirstFit(mainData.getItems()).solve();
			long stopTime = System.currentTimeMillis();
			print("First fit algorithm: \t\t" + dmff.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
		}
		if(bfRandom.isSelected()){
			long startTime = System.currentTimeMillis();
			dmbf = new BruteForce(mainData.getItems()).solve();
			long stopTime = System.currentTimeMillis();
			print("BruteForce algorithm: \t" + dmbf.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
		}
		if(babRandom.isSelected()){
			long startTime = System.currentTimeMillis();
			dmbab = new BranchAndBound(mainData.getItems()).solve();
			long stopTime = System.currentTimeMillis();
			if(!Parameters.stop)print("B & B algorithm: \t\t" + dmbab.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
			
		}
		if(nfInc.isSelected() || ffInc.isSelected() || bfInc.isSelected() || babInc.isSelected()){
			print("\nRosn�co: ",resultArea);
			mainData.sortInc();
		}
		if(nfInc.isSelected()){
			long startTime = System.currentTimeMillis();
			dmnfInc = new NextFit(mainData.getItemsInc()).solve();
			long stopTime = System.currentTimeMillis();
			print("Next fit algorithm: \t\t" + dmnfInc.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
		}
		if(ffInc.isSelected()){
			long startTime = System.currentTimeMillis();
			dmffInc = new FirstFit(mainData.getItemsInc()).solve();
			long stopTime = System.currentTimeMillis();
			print("First fit algorithm: \t\t" + dmffInc.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
		}
		if(bfInc.isSelected()){
			long startTime = System.currentTimeMillis();
			dmbfInc = new BruteForce(mainData.getItemsInc()).solve();
			long stopTime = System.currentTimeMillis();
			print("BruteForce algorithm: \t" + dmbfInc.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
		}
		if(babInc.isSelected()){
			long startTime = System.currentTimeMillis();
			dmbabInc = new BranchAndBound(mainData.getItemsInc()).solve();
			long stopTime = System.currentTimeMillis();
			if(!Parameters.stop)print("B & B algorithm: \t\t" + dmbabInc.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
		}

		if(nfDec.isSelected() || ffDec.isSelected() || bfDec.isSelected() || babDec.isSelected()){
			print("\nMalej�co: ",resultArea);
			mainData.sortDec();
		}

		if(nfDec.isSelected()){
			long startTime = System.currentTimeMillis();
			dmnfDec = new NextFit(mainData.getItemsDec()).solve();
			long stopTime = System.currentTimeMillis();
			print("Next fit algorithm: \t\t" + dmnfDec.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
		}

		if(ffDec.isSelected()){
			long startTime = System.currentTimeMillis();
			dmffDec = new FirstFit(mainData.getItemsDec()).solve();
			long stopTime = System.currentTimeMillis();
			print("First fit algorithm: \t\t" + dmffDec.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
		}

		if(bfDec.isSelected()){
			long startTime = System.currentTimeMillis();
			dmbfDec = new BruteForce(mainData.getItemsDec()).solve();
			long stopTime = System.currentTimeMillis();
			print("BruteForce algorithm: \t" + dmbfDec.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
		}
		if(babDec.isSelected()){
			long startTime = System.currentTimeMillis();
			dmbabDec = new BranchAndBound(mainData.getItemsDec()).solve();
			long stopTime = System.currentTimeMillis();
			if(!Parameters.stop)print("B & B algorithm: \t\t" + dmbabDec.getResult() + " (Czas: " + (stopTime - startTime) + "ms)",resultArea);
		}
	}
	
	private void printContainers(){
		if(Parameters.itemsNumber<5000){
		
			if(nfRandom.isSelected()){
				print("\n================================",resultArea);
				print("Next Fit losowo: ", resultArea);
				print("================================",resultArea);
				print(dmnf.printContainers(),resultArea);
			}
			if(ffRandom.isSelected()){
				print("\n================================",resultArea);
				print("First Fit losowo: ", resultArea);
				print("================================",resultArea);
				print(dmff.printContainers(),resultArea);
			}
			if(bfRandom.isSelected()){
				print("\n================================",resultArea);
				print("Brute Force losowo: ", resultArea);
				print("================================",resultArea);
				print(dmbf.printContainers(),resultArea);
			}
			if(babRandom.isSelected()){
				print("\n================================",resultArea);
				print("B & B losowo: ", resultArea);
				print("================================",resultArea);
				print(dmbab.printContainers(),resultArea);
			}
			if(nfInc.isSelected()){
				print("\n================================",resultArea);
				print("Next Fit rosn�co: ", resultArea);
				print("================================",resultArea);
				print(dmnfInc.printContainers(),resultArea);
			}
			if(ffInc.isSelected()){
				print("\n================================",resultArea);
				print("First Fit rosn�co: ", resultArea);
				print("================================",resultArea);
				print(dmffInc.printContainers(),resultArea);
			}
			if(bfInc.isSelected()){
				print("\n================================",resultArea);
				print("Brute Force rosn�co: ", resultArea);
				print("================================",resultArea);
				print(dmbfInc.printContainers(),resultArea);
			}
			if(babInc.isSelected()){
				print("\n================================",resultArea);
				print("B & B rosn�co: ", resultArea);
				print("================================",resultArea);
				print(dmbabInc.printContainers(),resultArea);
			}
			if(nfDec.isSelected()){
				print("\n================================",resultArea);
				print("Next Fit malej�co: ", resultArea);
				print("================================",resultArea);
				print(dmnfDec.printContainers(),resultArea);
			}
			if(ffDec.isSelected()){
				print("\n================================",resultArea);
				print("First Fit malej�co: ", resultArea);
				print("================================",resultArea);
				print(dmffDec.printContainers(),resultArea);
			}
			if(bfDec.isSelected()){
				print("\n================================",resultArea);
				print("Brute Force malej�co: ", resultArea);
				print("================================",resultArea);
				print(dmbfDec.printContainers(),resultArea);
			}
			if(babDec.isSelected()){
				print("\n================================",resultArea);
				print("B & B malej�co: ", resultArea);
				print("================================",resultArea);
				print(dmbabDec.printContainers(),resultArea);
			}
		}else print("Zbyt du�o przedmiot�w �eby wy�wietli� kontenery", resultArea);
	}
	
	
	void setDefaultValues(){
		minWeightField.setText("5");
		maxWeightField.setText("15");
		itemsNumberField.setText("13");
		containerVolumeField.setText("20");
	}
	
	void print(String s, TextArea ta){
		Platform.runLater( () -> ta.appendText(s + "\n") );
	}


	public void showStage(Parent root, Stage stage){
		 Scene scene = new Scene(root);
		 stage.setScene(scene);
		 stage.show();
	}
		
}
