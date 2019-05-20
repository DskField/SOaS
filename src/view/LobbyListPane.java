package view;

import java.util.ArrayList;

import client.Lobby;
import game.Player;
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

	// Magic Numbers
	final private static int LabelSize = 30;
	final private static int textSize = 25;
	final private static int titleLabelSize = 50;
	final private static Color statsBackgroundColor = Color.AQUAMARINE;
	final private static int statsBoxWidth = 400;
	final private static int statsBoxHeight = 400;
	final private static int bottomLineSpacing = 50;
	final private static int statsPaneSpacing = 20;
	final private static int lobbyJoinPannelSpacing = 50;
	final private static int joinGameButtonWidth = 400;
	final private static int joinGameButtonHeight = 150;

	public LobbyListPane(ArrayList<Lobby> lobbies, ClientScene clientscene) {
		this.clientscene = clientscene;
		lobbyList = new ListView<ToggleButton>();
		togglegroup = new ToggleGroup();
		this.handlebutton = new HandleButton();

		for (Lobby lob : lobbies) {
			ToggleButton togglebutton = new ToggleButton("Uitdaging " + lob.getGameID());
			togglebutton.setAlignment(Pos.CENTER);
			togglebutton.setOnMouseClicked(handlebutton);
			lobbyList.getItems().add(togglebutton);
			togglegroup.getToggles().add(togglebutton);
		}

		this.setLeft(lobbyList);
	}

	public void createStats(int idGame) {
		BorderPane statsBox = new BorderPane();

		Label titleLabel = new Label("Lobby " + idGame);
		titleLabel.setFont(Font.font(titleLabelSize));

		Label gamestateLabel = new Label("Game Status:");
		gamestateLabel.setFont(Font.font(LabelSize));

		Label gamestateTextLabel = new Label(clientscene.getLobby(idGame).getGameState());
		gamestateTextLabel.setFont(Font.font(textSize));

		// Added an extra VBox to get a different spacing between the two labels
		VBox gamestateBox = new VBox();
		gamestateBox.getChildren().addAll(gamestateLabel, gamestateTextLabel);
		gamestateBox.setAlignment(Pos.CENTER);

		// Scoreboard
		Label scoreboardLabel = new Label("Scoreboard:");
		scoreboardLabel.setFont(Font.font(LabelSize));

		VBox playerList = new VBox();
		playerList.setAlignment(Pos.CENTER);
		playerList.getChildren().add(scoreboardLabel);
		for (Player p : clientscene.getPlayers(idGame)) {
			Label playername = new Label(p.getUsername() + ": " + Integer.toString(clientscene.getScore(idGame, p)));
			playername.setFont(Font.font(textSize));
			playerList.getChildren().add(playername);
		}

		Label rondeLabel = new Label("Ronde: " + clientscene.getLobby(idGame).getCurrentRound());
		rondeLabel.setFont(Font.font(LabelSize));

		String won;
		if (clientscene.getLobby(idGame).getGameState().equals("uitgespeeld"))
			won = clientscene.getLobby(idGame).isWon() ? "Gewonnen" : "Verloren";
		else
			won = String.format("%0$-22s", "");
		// won = String.format("%0$-15s", won);
		Label wonLabel = new Label(won);
		wonLabel.setFont(Font.font(LabelSize));

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

		VBox lobbyJoinPannel = new VBox();
		lobbyJoinPannel.setSpacing(lobbyJoinPannelSpacing);
		lobbyJoinPannel.getChildren().addAll(statsBox, joinGameButton);

		this.setCenter(lobbyJoinPannel);
	}

	public void joinGameButton(int idGame) {
		clientscene.joinGame(idGame);
	}

	private class HandleButton implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			String[] lobby = ((ToggleButton) e.getSource()).getText().split(" ");
			int idGame = Integer.parseInt(lobby[1]);
			System.out.println(idGame);
			createStats(idGame);
		}
	}

}