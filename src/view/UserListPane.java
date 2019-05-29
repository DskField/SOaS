package view;

import java.util.ArrayList;

import client.User;
import game.GameColor;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class UserListPane extends BorderPane {

	private ClientScene clientscene;
	private ListView<BorderPane> userList;
	private ToggleGroup togglegroup;
	private ArrayList<String> users;
	private HandleRadioButton handleradiobutton;
	private ArrayList<CheckBox> inviteCheckBoxGroup;
	private Label errorMessage;
	private Button invitePlayers;
	private String opponentUsername;
	private boolean useRandomChecked = false;
	private CheckBox useRandomPatternCards;

	// Magic Numbers
	final private static Color statsBackgroundColor = Color.AQUAMARINE;
	final private static int statsBoxWidth = 400;
	final private static int statsBoxHeight = 340;
	final private static int usernameLabelSize = 50;
	final private static int textSize = 25;
	final private static int inviteButtonSize = 40;
	final private static int errorMessageSize = 23;
	final private static Color errorMessageColor = Color.RED;
	final private static int viewStatsWidth = 70;
	final private static int viewStatsHeight = 30;
	final private static int inviteRadioButtonWidth = 100;
	final private static int inviteRadioButtonHeight = 30;
	final private static int listWidth = 250;
	final private static int listHeight = (int) Screen.getPrimary().getBounds().getMaxY();

	public UserListPane(ClientScene clientscene) {
		this.clientscene = clientscene;
		this.userList = new ListView<BorderPane>();
		this.togglegroup = new ToggleGroup();
		this.handleradiobutton = new HandleRadioButton();
		this.inviteCheckBoxGroup = new ArrayList<CheckBox>();
		this.opponentUsername = "";

		createLeft();
		createCenter(opponentUsername);
		errorMessage.setVisible(false);
	}

	public void createLeft() {
		userList.getItems().clear();
		userList.setMinSize(listWidth, listHeight);
		userList.setMaxSize(listWidth, listHeight);
		togglegroup.getToggles().clear();
		users = clientscene.getUsers();

		// Remove yourself from selectable list
		users.remove(clientscene.getUsername());

		for (String username : users) {
			CheckBox inviteRadioButton = new CheckBox();
			inviteRadioButton.setOnMouseClicked(handleradiobutton);
			inviteRadioButton.setText(username);
			inviteRadioButton.setMinSize(inviteRadioButtonWidth, inviteRadioButtonHeight);
			inviteRadioButton.setMaxSize(inviteRadioButtonWidth, inviteRadioButtonHeight);

			Button viewStats = new Button("vergelijk");
			viewStats.setMinSize(viewStatsWidth, viewStatsHeight);
			viewStats.setMaxSize(viewStatsWidth, viewStatsHeight);
			viewStats.setOnAction(e -> createCenter(username));

			for (CheckBox box : inviteCheckBoxGroup) {
				if (box.getText().equals(inviteRadioButton.getText()))
					inviteRadioButton.setSelected(true);
			}

			BorderPane playerLine = new BorderPane();
			playerLine.setLeft(inviteRadioButton);
			playerLine.setRight(viewStats);
			userList.getItems().add(playerLine);
		}
		this.setLeft(userList);
	}

	public void createCenter(String opponentUsername) {
		this.opponentUsername = opponentUsername;
		VBox centerpane = new VBox();

		errorMessage = new Label("Je hebt een speler als recent uitgenodigd");
		errorMessage.setFont(Font.font(errorMessageSize));
		errorMessage.setTextFill(errorMessageColor);
		errorMessage.setAlignment(Pos.CENTER);

		invitePlayers = new Button("Spelers uitnodigen");
		invitePlayers.setMinWidth(statsBoxWidth);
		invitePlayers.setMaxWidth(statsBoxWidth);
		invitePlayers.setFont(Font.font(inviteButtonSize));
		invitePlayers.setOnAction(e -> handleInvitePlayers());
		invitePlayers.setDisable(true);

		useRandomPatternCards = new CheckBox("Gebruik random patroonkaarten");
		useRandomPatternCards.setAlignment(Pos.TOP_CENTER);
		useRandomPatternCards.setSelected(useRandomChecked);

		VBox buttonAndLabel = new VBox();
		buttonAndLabel.getChildren().addAll(errorMessage, invitePlayers, useRandomPatternCards);
		buttonAndLabel.setAlignment(Pos.CENTER);

		centerpane.getChildren().addAll(createStatsPane(clientscene.getUser()),
				createStatsPane(clientscene.getOpponent(opponentUsername)), buttonAndLabel);
		centerpane.setAlignment(Pos.CENTER);
		centerpane.setSpacing(50);

		// at first when no opponent is selected hide stats
		if (opponentUsername.equals(""))
			centerpane.getChildren().get(1).setVisible(false);
		else
			centerpane.getChildren().get(1).setVisible(true);

		this.setCenter(centerpane);
	}

	private BorderPane createStatsPane(User user) {
		BorderPane bp = new BorderPane();
		bp.setBackground(new Background(new BackgroundFill(statsBackgroundColor, null, null)));

		Label username = new Label(user.getUsername());
		username.setFont(Font.font(usernameLabelSize));

		Label gamesPlayed = new Label("Potjes gespeeld: " + Integer.toString(user.getGamesPlayed()));
		gamesPlayed.setFont(Font.font(textSize));

		Label gamesWon = new Label("Potjes gewonnen: " + Integer.toString(user.getGamesWon()));
		gamesWon.setFont(Font.font(textSize));

		Label gamesLost = new Label("Potjes verloren: " + Integer.toString(user.getGamesLost()));
		gamesLost.setFont(Font.font(textSize));

		Label maxScore = new Label("Hoogste score: " + Integer.toString(user.getMaxScore()));
		maxScore.setFont(Font.font(textSize));

		Label mostPlacedColor = new Label("Meest geplaatste kleur: " + user.getMostPlacedColor().getDatabaseName());
		mostPlacedColor.setFont(Font.font(textSize));

		Label mostPlacedValue = new Label("Meest geplaatste waarde: " + Integer.toString(user.getMostPlacedValue()));
		mostPlacedValue.setFont(Font.font(textSize));

		Label totalOpponents = new Label("Aantal tegenstanders: " + Integer.toString(user.getTotalOpponents()));
		totalOpponents.setFont(Font.font(textSize));

		VBox labelBox = new VBox();
		labelBox.getChildren().addAll(username, gamesPlayed, gamesWon, gamesLost, maxScore, mostPlacedColor,
				mostPlacedValue, totalOpponents);
		labelBox.setAlignment(Pos.CENTER);
		bp.setCenter(labelBox);
		bp.setMinSize(statsBoxWidth, statsBoxHeight);
		bp.setMaxSize(statsBoxWidth, statsBoxHeight);

		return bp;
	}

	private void handleInvitePlayers() {
		ArrayList<User> result = new ArrayList<>();

		result.add(clientscene.getUser());
		for (CheckBox box : inviteCheckBoxGroup) {
			result.add(new User(box.getText(), 0, 0, GameColor.EMPTY, 0, 0, 0, 0));
		}
		clientscene.updateClient();
		if (clientscene.createGame(result, useRandomPatternCards.isSelected())) 
			errorMessage.setVisible(false);
		else
			errorMessage.setVisible(true);
	}

	private class HandleRadioButton implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			// handle deselect
			if (!((CheckBox) e.getSource()).isSelected()) {
				for (int i = 0; i < inviteCheckBoxGroup.size(); i++) {
					if (inviteCheckBoxGroup.get(i).getText().equals(((CheckBox) e.getSource()).getText()))
						inviteCheckBoxGroup.remove(i);
				}
			} else {
				// you can only select 3
				if (inviteCheckBoxGroup.size() == 3) {
					inviteCheckBoxGroup.get(0).setSelected(false);
					inviteCheckBoxGroup.remove(0);
				}
				inviteCheckBoxGroup.add((CheckBox) e.getSource());
			}

			// when no opponents are selected, disable invite button
			if (inviteCheckBoxGroup.size() == 0)
				invitePlayers.setDisable(true);
			else
				invitePlayers.setDisable(false);
		}
	}
}
