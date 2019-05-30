package view;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LobbyListPane extends BorderPane {

	private ListView<ToggleButton> lobbyList;
	private ToggleGroup togglegroup;
	private HandleButton handlebutton;
	private ClientScene clientscene;
	private Label errorMessage;
	private ArrayList<Integer> lobbies;
	private int idGame;

	// All stats labels
	private Label titleLabel;
	private Label gamestateLabel;
	private Label gamestateTextLabel;
	private Label scoreboardLabel;
	private Label rondeLabel;
	private Label wonLabel;

	// Magic Numbers
	final private int labelSize = 30;
	final private int textSize = 25;
	final private int titleLabelSize = 50;
	final private Color statsBackgroundColor = Color.AQUAMARINE;
	final private int statsBoxWidth = 400;
	final private int statsBoxHeight = 400;
	final private int bottomLineSpacing = 50;
	final private int statsPaneSpacing = 10;
	final private int lobbyJoinPannelSpacing = 50;
	final private int joinGameButtonWidth = 400;
	final private int joinGameButtonHeight = 150;
	final private Color errorMessageColor = Color.RED;

	public LobbyListPane(ClientScene clientscene) {
		this.clientscene = clientscene;
		lobbyList = new ListView<ToggleButton>();
		togglegroup = new ToggleGroup();
		this.handlebutton = new HandleButton();
		idGame = 0;
		createLeft();
	}

	public void createLeft() {
		lobbyList.getItems().clear();
		togglegroup.getToggles().clear();
		this.lobbies = clientscene.getLobbies();

		for (Integer lob : lobbies) {
			ToggleButton togglebutton = new ToggleButton("Game " + lob);
			togglebutton.setAlignment(Pos.CENTER);
			togglebutton.setOnMouseClicked(handlebutton);
			lobbyList.getItems().add(togglebutton);
			togglegroup.getToggles().add(togglebutton);
		}

		this.setLeft(lobbyList);
	}

	public void createStats(int idGame) {
		BorderPane statsBox = new BorderPane();

		titleLabel = new Label("Lobby " + idGame);
		titleLabel.setFont(Font.font(titleLabelSize));

		gamestateLabel = new Label("Game Status:");
		gamestateLabel.setFont(Font.font(labelSize));

		// check if gamestate is 'aan de gang'
		if (clientscene.getLobby(idGame).getGameState().equals("aan de gang")) {
			// check if cards are given to start the game else change gamestate label
			if (clientscene.isGameReady(idGame)) {
				gamestateTextLabel = new Label("aan de gang");
			} else {
				gamestateTextLabel = new Label("wachtende");
			}
		} else {
			gamestateTextLabel = new Label(clientscene.getLobby(idGame).getGameState());
		}

		gamestateTextLabel.setFont(Font.font(textSize));

		// Added an extra VBox to get a different spacing between the two labels
		VBox gamestateBox = new VBox();
		gamestateBox.getChildren().addAll(gamestateLabel, gamestateTextLabel);
		gamestateBox.setAlignment(Pos.CENTER);

		// Scoreboard
		scoreboardLabel = new Label("Scoreboard:");
		scoreboardLabel.setFont(Font.font(labelSize));

		VBox playerList = new VBox();
		playerList.setAlignment(Pos.CENTER);
		playerList.getChildren().add(scoreboardLabel);

		ArrayList<ArrayList<String>> scoreboardList = clientscene.getScore(idGame, clientscene.getPlayers(idGame));
		for (int i = 0; i < scoreboardList.size(); i++) {
			Label playername = new Label((i + 1) + ". " + scoreboardList.get(i).get(0) + ": " + scoreboardList.get(i).get(1));
			playername.setFont(Font.font(textSize));
			playerList.getChildren().add(playername);
		}

		rondeLabel = new Label("Ronde: " + (clientscene.getLobby(idGame).getCurrentRound() - 1));
		rondeLabel.setFont(Font.font(labelSize));

		String won;
		if (clientscene.getLobby(idGame).getGameState().equals("uitgespeeld"))
			won = clientscene.getLobby(idGame).isWon() ? "Gewonnen" : "Verloren";
		else
			won = String.format("%0$-22s", "");
		wonLabel = new Label(won);
		wonLabel.setFont(Font.font(labelSize));

		VBox statsPane = new VBox();
		statsPane.setSpacing(statsPaneSpacing);

		// BottomLine ronde + won/lost
		HBox bottomLine = new HBox();
		bottomLine.getChildren().addAll(rondeLabel, wonLabel);
		bottomLine.setSpacing(bottomLineSpacing);
		bottomLine.setAlignment(Pos.CENTER);

		statsPane.getChildren().addAll(titleLabel, gamestateBox, playerList);
		statsPane.setAlignment(Pos.TOP_CENTER);
		statsBox.setCenter(statsPane);
		// Set on bottom since the scoreboard size can change, but we want to keep the
		// same layout
		statsBox.setBottom(bottomLine);
		statsBox.setBackground(new Background(new BackgroundFill(statsBackgroundColor, null, null)));
		statsBox.setMinSize(statsBoxWidth, statsBoxHeight);
		statsBox.setMaxSize(statsBoxWidth, statsBoxHeight);

		Button joinGameButton = new Button("Game Betreden");
		joinGameButton.setOnAction(e -> joinGameButton(idGame));
		joinGameButton.setMinSize(joinGameButtonWidth, joinGameButtonHeight);
		joinGameButton.setMaxSize(joinGameButtonWidth, joinGameButtonHeight);

		errorMessage = new Label();
		errorMessage.setFont(Font.font(textSize));
		errorMessage.setAlignment(Pos.CENTER);
		errorMessage.setTextFill(errorMessageColor);
		errorMessage.setVisible(false);

		VBox lobbyJoinPannel = new VBox();
		lobbyJoinPannel.setSpacing(lobbyJoinPannelSpacing);
		lobbyJoinPannel.getChildren().addAll(statsBox, joinGameButton, errorMessage);
		lobbyJoinPannel.setAlignment(Pos.CENTER);

		this.setCenter(lobbyJoinPannel);
	}

	public int getIDGame() {
		return idGame;
	}

	public void joinGameButton(int idGame) {
		if (gamestateTextLabel.getText().equals("aan de gang") || gamestateTextLabel.getText().equals("uitgespeeld")) {
			errorMessage.setVisible(false);
			clientscene.joinGame(idGame);
		} else {
			errorMessage.setText(gamestateTextLabel.getText().equals("afgebroken") ? "het potje is afgebroken" : "Het potje is nog niet begonnen");
			errorMessage.setVisible(true);
		}
	}

	private class HandleButton implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			String[] lobby = ((ToggleButton) e.getSource()).getText().split(" ");
			idGame = Integer.parseInt(lobby[1]);
			// TODO TOM only update the variables and set errormessage visible to false
			createStats(idGame);
		}
	}

}