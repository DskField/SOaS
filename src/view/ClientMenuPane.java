package view;

import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ClientMenuPane extends VBox {

	private ClientScene clientscene;
	private ToggleButton userListButton;
	private ToggleButton lobbyListButton;
	private ToggleButton challengeListButton;
	private ToggleButton logoutButton;

	// Magic Numbers
	final private static int buttonWidth = 300;
	final private static int buttonHeight = 100;

	public ClientMenuPane(ClientScene clientscene) {
		super();
		this.clientscene = clientscene;
		this.setAlignment(Pos.TOP_CENTER);
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		createButtons();
	}

	private void createButtons() {
		userListButton = new ToggleButton("Spelers");
		userListButton.setMinSize(buttonWidth, buttonHeight);
		userListButton.setMaxSize(buttonWidth, buttonHeight);
		userListButton.setOnAction(e -> clientscene.handleUserListButton());

		lobbyListButton = new ToggleButton("Lobby's");
		lobbyListButton.setMinSize(buttonWidth, buttonHeight);
		lobbyListButton.setMaxSize(buttonWidth, buttonHeight);
		lobbyListButton.setOnAction(e -> clientscene.handleLobbyListButton());

		challengeListButton = new ToggleButton("Uitdaging");
		challengeListButton.setMinSize(buttonWidth, buttonHeight);
		challengeListButton.setMaxSize(buttonWidth, buttonHeight);
		challengeListButton.setOnAction(e -> clientscene.handleChallengeListButton());

		logoutButton = new ToggleButton("Uitloggen");
		logoutButton.setMinSize(buttonWidth, buttonHeight);
		logoutButton.setMaxSize(buttonWidth, buttonHeight);
		logoutButton.setOnAction(e -> clientscene.logOut());

		ToggleGroup togglegroup = new ToggleGroup();
		togglegroup.getToggles().addAll(userListButton, lobbyListButton, challengeListButton, logoutButton);

		this.getChildren().addAll(userListButton, lobbyListButton, challengeListButton, logoutButton);
	}
}