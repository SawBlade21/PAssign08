package passign08b;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PAssign08b extends Application {

	public void start(Stage primaryStage) {
		
		Label title = new Label("Tic Tac Toe");
		title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40));
		
		TicTacToePane gamePane = new TicTacToePane();
		
		Label score = new Label("Placeholder");
		score.textProperty().bind(gamePane.getScore());
		score.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40));
		score.setAlignment(Pos.CENTER_LEFT);
		score.setPadding(new Insets(0, 0, 0, 250));
		
		Button reset = new Button("Reset");
		reset.setOnAction((e) -> gamePane.reset());
		reset.setScaleX(3);
		reset.setScaleY(3);
		reset.setAlignment(Pos.CENTER_RIGHT);
		
		GridPane gp = new GridPane();
		gp.add(score, 1, 0);
		gp.add(reset, 0, 0);
		gp.setMinWidth(750);
		gp.setPadding(new Insets(50, 50, 50, 50));
		gp.setAlignment(Pos.CENTER);
		
		VBox vb = new VBox();
		vb.getChildren().addAll(title, gamePane, gp);
		vb.setAlignment(Pos.CENTER);
		// vb.setMargin(hb,  new Insets(0, 10, 0, 10));
		// vb.setPadding(new Insets(0, 0, 10, 0));
		
		BorderPane bp = new BorderPane();
		bp.setCenter(vb);
		// Create a scene and place it in the stage
		Scene scene = new Scene(bp, 750, 1000);
		primaryStage.setTitle("MPG Calculator"); // Set title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

class TicTacToePane extends GridPane {
	private int turn = 0;
	private ArrayList<Button> btns;
	private StringProperty score;
	private boolean gameOver;
	
	public TicTacToePane() {
		score = new SimpleStringProperty("Winner: ");
		gameOver = false;
		this.setAlignment(Pos.CENTER);
		this.setHgap(50);
		this.setVgap(50);
		this.setStyle("-fx-border-color: black; -fx-border-width: 25; -fx-border-style: solid;");
		this.setPrefSize(750,  750);
		this.setMinWidth(750);
		this.setMinHeight(750);
		
		btns = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Button btn = new Button();
				btn.setPrefSize(200, 200);
				btn.setStyle("-fx-font-size: 80px; -fx-text-fill: white; -fx-background-color: black; -fx-border-color: black; -fx-border-width: 2");
				
				btn.setOnAction((e) -> onPress(btn));
				
				btns.add(btn);
				this.add(btn, j, i);
			}
		}
	}
	
	private void onPress(Button btn) {
		// System.out.println(turn);
		if (gameOver)
			return;
		
		if (turn >= 9) {
			endGame();
			return;
		}
		
		int index = btns.indexOf(btn);
		
		if (btn.getText() == "") {
			if (turn % 2 == 0) {
				btn.setText("X");
			}
			else {
				btn.setText("O");
			}
		}
		
		turn++;
		checkScore();
	}
	
	public void reset() {
		for (Button btn : btns) {
			btn.setText("");
		}
		turn = 0;
		score.set("Winner: ");
		gameOver = false;
	}
	
	private void endGame() {
		gameOver = true;
	}
	
	private void checkScore() {
		int offset = 0;
		
		// horizontals
		for (int i = 0; i < 3; i++) {
			String inputs = "";
			for (int j = 0; j < 3; j++) {
				inputs += btns.get(offset + j).getText();
			}
			
			System.out.println(inputs);
			
			if (inputs.equals("XXX")) {
				score.set("Winner: X");
				endGame();
				break;
			}
			else if (inputs.equals("OOO")) {
				score.set("Winner: O");
				endGame();
				break;
			}
			
			offset += 3;
		}
		
		// verticals
		offset = 0;
		for (int i = 0; i < 3; i++) {
			String inputs = "";
			for (int j = 0; j < 3; j++) {
				inputs += btns.get(offset + (j * 3)).getText();
			}
			
			System.out.println(inputs);
			
			if (inputs.equals("XXX")) {
				score.set("Winner: X");
				endGame();
				break;
			}
			else if (inputs.equals("OOO")) {
				score.set("Winner: O");
				endGame();
				break;
			}
			
			offset++;
		}
		
		// down diagonal
		String down = "";
		for (int i = 0; i < 3; i++) {
			down += btns.get(i * 4).getText();
		}
		
		if (down.equals("XXX")) {
			score.set("Winner: X");
			endGame();
		}
		else if (down.equals("OOO")) {
			score.set("Winner: O");
			endGame();
		}
		
		String up = "";
		for (int i = 0; i < 3; i++) {
			up += btns.get(i * 2).getText();
		}
		
		if (up.equals("XXX")) {
			score.set("Winner: X");
			endGame();
		}
		else if (up.equals("OOO")) {
			score.set("Winner: O");
			endGame();
		}
	
		
		System.out.println("Score: " + score);
	}
	
	public StringProperty getScore() {
		return score;
	}
}
