package view;

import java.util.ArrayList;

import controllers.MainApplication;
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
import javafx.stage.Screen;

public class LobbyListPane extends BorderPane {
	/* CONSTANTS */
	// Left
	private final Background togglebuttonBackground = new Background(new BackgroundFill(Color.BLUE, null, null));
	private final Color togglebuttonColor = Color.WHITE;
	private final double orderButtonHeight = 45 * MainApplication.height;;
	private final double lobbyListHeight = Screen.getPrimary().getVisualBounds().getMaxY() - orderButtonHeight - 5;

	// Stats
	private final double labelSize = 30 * MainApplication.height;;
	private final double textSize = 25 * MainApplication.height;;
	private final double titleLabelSize = 50 * MainApplication.height;;
	private final Color statsBackgroundColor = Color.AQUAMARINE;
	private final double statsBoxWidth = 400 * MainApplication.width;
	private final double statsBoxHeight = 400 * MainApplication.height;;
	private final double bottomLineSpacing = 50 * MainApplication.height;;
	private final double statsPaneSpacing = 10 * MainApplication.height;;
	private final double lobbyJoinPannelSpacing = 50 * MainApplication.height;;
	private final double joinGameButtonWidth = 400 * MainApplication.width;
	private final double joinGameButtonHeight = 150 * MainApplication.height;;
	private final Color errorMessageColor = Color.RED;

	/* VARIABLES */
	private ClientScene clientscene;
	private int idGame;

	// Lefts
	private ListView<ToggleButton> lobbyList;
	private ToggleGroup togglegroup;
	private HandleButton handlebutton;
	private ArrayList<Integer> lobbies;
	private ArrayList<Integer> playerLobbies;
	private boolean orderASC;
	private Button orderButton;

	// Stats
	private Label errorMessage;
	private ArrayList<ArrayList<String>> scoreboardList;
	private Label titleLabel;
	private Label gamestateLabel;
	private Label gamestateTextLabel;
	private Label scoreboardLabel;
	private Label rondeLabel;
	private Label wonLabel;

	/**
	 * Constructor used to create a {@code LobbyListPane}
	 * 
	 * @param clientscene - Contains the reference to {@code ClientScene}
	 */
	public LobbyListPane(ClientScene clientscene) {
		this.clientscene = clientscene;
		lobbyList = new ListView<ToggleButton>();
		togglegroup = new ToggleGroup();
		this.handlebutton = new HandleButton();
		idGame = 0;
		orderASC = true;
		createLeft();
	}

	/**
	 * Method used to create the List of lobbies and sort button
	 */
	public void createLeft() {
		lobbyList.getItems().clear();
		togglegroup.getToggles().clear();
		this.lobbies = clientscene.getLobbies();
		this.playerLobbies = clientscene.getPlayerLobbies();

		orderButton = new Button(orderASC ? "gesorteerd op oudste" : "gesorteerd op nieuwste");
		orderButton.setOnAction(e -> handleOrderButton());
		orderButton.setMinHeight(orderButtonHeight);
		orderButton.setMaxHeight(orderButtonHeight);

		for (Integer lob : lobbies) {
			ToggleButton togglebutton = new ToggleButton("Spel " + lob);
			togglebutton.setAlignment(Pos.CENTER);
			togglebutton.setOnMouseClicked(handlebutton);
			if (playerLobbies.contains(lob)) {
				togglebutton.setBackground(togglebuttonBackground);
				togglebutton.setTextFill(togglebuttonColor);
			}
			lobbyList.getItems().add(togglebutton);
			togglegroup.getToggles().add(togglebutton);
		}

		lobbyList.setMinHeight(lobbyListHeight);
		lobbyList.setMaxHeight(lobbyListHeight);
		orderButton.setMinWidth(lobbyList.getWidth());
		orderButton.setMaxWidth(lobbyList.getWidth());

		VBox leftBox = new VBox();
		leftBox.getChildren().addAll(orderButton, lobbyList);
		leftBox.setAlignment(Pos.CENTER);

		this.setLeft(leftBox);
	}

	/**
	 * Method used to create the stats pane, the error message, the join button
	 * 
	 * @param idGame - {@code int} containing the idGame used to get the data for the stats and to join
	 * the game
	 */
	public void createStats(int idGame) {
		BorderPane statsBox = new BorderPane();

		titleLabel = new Label("Spel " + idGame);
		titleLabel.setFont(Font.font(titleLabelSize));

		gamestateLabel = new Label("Spel Status:");
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
		scoreboardLabel = new Label("Scorebord:");
		scoreboardLabel.setFont(Font.font(labelSize));

		VBox playerList = new VBox();
		playerList.setAlignment(Pos.CENTER);
		playerList.getChildren().add(scoreboardLabel);

		// Makes scoreboard when game isn't finished and has no score in database
		scoreboardList = gamestateTextLabel.getText().equals("uitgespeeld") ? clientscene.getScoreboard(idGame) : clientscene.getScore(idGame, clientscene.getPlayers(idGame));
		for (int i = 0; i < scoreboardList.size(); i++) {
			Label playername = new Label((i + 1) + ". " + scoreboardList.get(i).get(0) + ": " + scoreboardList.get(i).get(1));
			playername.setFont(Font.font(textSize));
			playerList.getChildren().add(playername);
		}

		rondeLabel = new Label("Ronde: " + (clientscene.getLobby(idGame).getCurrentRound() - 1));
		rondeLabel.setFont(Font.font(labelSize));

		String won;
		if (clientscene.getLobby(idGame).getGameState().equals("uitgespeeld"))
			won = scoreboardList.get(0).get(0).equals(clientscene.getUser().getUsername()) ? "Gewonnen" : "Verloren";
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

		Button joinGameButton = new Button("Spel Betreden");
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

	/* GETTERS AND SETTERS */
	public int getIDGame() {
		return idGame;
	}

	/**
	 * Method used to join a game
	 * 
	 * @param idGame - int containing the idGame the user is trying to join
	 */
	public void joinGameButton(int idGame) {
		boolean playerInGame = false;
		for (ArrayList<String> list : scoreboardList) {
			if (list.get(0).equals(clientscene.getUser().getUsername())) {
				playerInGame = true;
			}
		}

		if (!playerInGame) {
			errorMessage.setText("Jij neemt niet deel aan dit spel");
			errorMessage.setVisible(true);
		} else {
			if ((gamestateTextLabel.getText().equals("aan de gang") || gamestateTextLabel.getText().equals("uitgespeeld"))) {
				errorMessage.setVisible(false);
				clientscene.joinGame(idGame);
			} else {
				errorMessage.setText(gamestateTextLabel.getText().equals("afgebroken") ? "het spel is afgebroken" : "Het spel is nog niet begonnen");
				errorMessage.setVisible(true);
			}
		}
	}

	/**
	 * Method used to sort the Usernames in the list
	 */
	private void handleOrderButton() {
		orderASC = orderASC ? false : true;
		clientscene.changeLobbyOrder(orderASC);
		createLeft();
	}

	/**
	 * Method used to create the statsPane based on the button
	 */
	private class HandleButton implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			String[] lobby = ((ToggleButton) e.getSource()).getText().split(" ");
			idGame = Integer.parseInt(lobby[1]);
			createStats(idGame);
		}
	}

}