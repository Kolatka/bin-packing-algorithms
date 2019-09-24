package gui;

import binpacking.*;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
	String containersMessage = "";
	private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startButton.setDisable(true);
		stopButton.setDisable(true);
		containerButton.setDisable(true);
		setDefaultValues();
		Parameters.stop = false;
		itemsArea.setStyle("-fx-font-size: 11");
		resultArea.setStyle("-fx-font-size: 11");
		packCheckBoxes();
	}

	private void packCheckBoxes() {
		checkBoxes.add(nfRandom);
		checkBoxes.add(ffRandom);
		checkBoxes.add(bfRandom);
		checkBoxes.add(babRandom);
		checkBoxes.add(nfDec);
		checkBoxes.add(ffDec);
		checkBoxes.add(bfDec);
		checkBoxes.add(babDec);
		checkBoxes.add(nfInc);
		checkBoxes.add(ffInc);
		checkBoxes.add(bfInc);
		checkBoxes.add(babInc);
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
				else print("Too many items to display",itemsArea);
				long stopTime = System.currentTimeMillis();
				print("\nGenerated in: " + (stopTime - startTime) + "ms\n",itemsArea);
				});
			thread.start();
			startButton.setDisable(false);
		}else if(event.getSource()==startButton){
			Parameters.stop = false;
			stopButton.setDisable(false);
			startButton.setDisable(true);
			Thread thread2 = new Thread(() -> {
				resultArea.clear();
				print("Items count: " + mainData.getItems().size(),resultArea);
				print("Items size: "+ mainData.getTotalWeight(),resultArea);
				print("Container size: "+ mainData.getContainerVolume(),resultArea);
				print("\nResults:\n",resultArea);
				print("Lower Bound: \t\t" + mainData.getLowerBound(),resultArea);
				solve();
				print("\nCompleted",resultArea);
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
			print(containersMessage, resultArea);
		}

	}

	private void solve() {
		for (int i = 0; i < checkBoxes.size(); i++) {
			CheckBox checkBox = checkBoxes.get(i);
			DataManager tempDataManager = new DataManager();
			String message = "";

			if (i == 0) {
				print("\nRandom: ", resultArea);
			} else if (i == 4) {
				mainData.sortInc();
				print("\nDecreasing: ", resultArea);
			} else if (i == 8) {
				mainData.sortDec();
				print("\nIncreasing: ", resultArea);
			}
			long startTime = System.currentTimeMillis();
			if (checkBox.getId().equals("nfRandom") && checkBox.isSelected()) {
				tempDataManager = new NextFit(mainData.getItems()).solve();
				message = "Next fit algorithm: \t\t";
			}
			if (checkBox.getId().equals("ffRandom") && checkBox.isSelected()) {
				tempDataManager = new FirstFit(mainData.getItems()).solve();
				message = "First fit algorithm: \t\t";
			}
			if (checkBox.getId().equals("bfRandom") && checkBox.isSelected()) {
				tempDataManager = new BruteForce(mainData.getItems()).solve();
				message = "BruteForce algorithm: \t";
			}
			if (checkBox.getId().equals("babRandom") && checkBox.isSelected()) {
				tempDataManager = new BranchAndBound(mainData.getItems()).solve();
				message = "B & B algorithm: \t\t";
			}
			if (checkBox.getId().equals("nfDec") && checkBox.isSelected()) {
				tempDataManager = new NextFit(mainData.getItems()).solve();
				message = "Next fit algorithm: \t\t";
			}
			if (checkBox.getId().equals("ffDec") && checkBox.isSelected()) {
				tempDataManager = new FirstFit(mainData.getItems()).solve();
				message = "First fit algorithm: \t\t";
			}
			if (checkBox.getId().equals("bfDec") && checkBox.isSelected()) {
				tempDataManager = new BruteForce(mainData.getItems()).solve();
				message = "BruteForce algorithm: \t";
			}
			if (checkBox.getId().equals("babDec") && checkBox.isSelected()) {
				tempDataManager = new BranchAndBound(mainData.getItems()).solve();
				message = "B & B algorithm: \t\t";
			}
			if (checkBox.getId().equals("nfInc") && checkBox.isSelected()) {
				tempDataManager = new NextFit(mainData.getItems()).solve();
				message = "Next fit algorithm: \t\t";
			}
			if (checkBox.getId().equals("ffInc") && checkBox.isSelected()) {
				tempDataManager = new FirstFit(mainData.getItems()).solve();
				message = "First fit algorithm: \t\t";
			}
			if (checkBox.getId().equals("bfInc") && checkBox.isSelected()) {
				tempDataManager = new BruteForce(mainData.getItems()).solve();
				message = "BruteForce algorithm: \t";
			}
			if (checkBox.getId().equals("babInc") && checkBox.isSelected()) {
				tempDataManager = new BranchAndBound(mainData.getItems()).solve();
				message = "B & B algorithm: \t\t";
			}
			long stopTime = System.currentTimeMillis();
			if (message.length() > 0) {
				containersMessage += getContainerString(message, i, tempDataManager);
				print(message + tempDataManager.getResult() + " (Time: " + (stopTime - startTime) + "ms)", resultArea);
			}
		}
	}

	private String getContainerString(String message, int i, DataManager dm) {
		StringBuilder sb = new StringBuilder();
		String type = "";
		if (i < 4) type = "random";
		else if (i < 8) type = "inc";
		else type = "dec";
		sb.append("\n================================\n");
		sb.append(message.substring(0, message.length() - 4) + " " + type);
		sb.append("\n================================");
		sb.append(dm.printContainers());
		return sb.toString();
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
