package view;

import java.util.ArrayList;

import controllers.GameController;
import game.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatPane extends BorderPane {
	// constants
	private final int chatPaneWidth = 400;
	private final int chatPaneheight = 200;
	private final int sendButtonWidth = 100;
	private final int sendButtonHeight = 20;

	// variables
	private TextField playerMessage;
	private Button sendMessage;

	private ScrollPane scrollPane;
	private HBox bottom;
	private VBox center;
	private GameController gameController;

	public ChatPane(GameController gameController) {
		// setting the GameController
		this.gameController = gameController;

		// handles the makeup of the ChatPane
		// setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
		setPrefSize(chatPaneWidth, chatPaneheight);

		// creates the Chat
		createChat();

	}

	/**
	 * Creates the entire chat. Including the send button and the player input bar.
	 */
	private void createChat() {
		// initialize
		playerMessage = new TextField();
		sendMessage = new Button("verstuur");
		scrollPane = new ScrollPane();
		bottom = new HBox();
		center = new VBox();

		// Handles makeup
		sendMessage.setPrefSize(sendButtonWidth, sendButtonHeight);
		playerMessage.setPrefWidth(chatPaneWidth - sendButtonWidth);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(center);

		// scrolls scrollbar to bottom
		scrollPane.setVvalue(1d);

		// sets everything to the ChatPane
		bottom.getChildren().addAll(playerMessage, sendMessage);
		setCenter(scrollPane);
		setBottom(bottom);

		// handlers

		// handles the Enter key for sending messages
		playerMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					sendMessage();
				} else if (event.getCode() == KeyCode.ESCAPE) {
					event.consume();
				}
			}
		});

		// handles the "verzenden"button to send messages
		sendMessage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sendMessage();
			}
		});

		// handles the focus for the scrollPane
		scrollPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

			}
		});

	}

	/**
	 * adds Messages to the Chat.
	 * 
	 * @param messages
	 *            - ArrayList<Message> this list of Messages will be added to the
	 *            chat
	 */
	public void updateChat(ArrayList<Message> messages) {
		if (messages.size() != 0) {
			for (Message message : messages) {
				center.getChildren()
						.add(new MessagePane(message.getUserName(), message.getMessage(), message.getChatTime()));
			}
			setScrollBottom();
		}
	}

	public void setScrollBottom() {
		scrollPane.setVvalue(1.5);
	}

	/**
	 * Gives the the String from playerMessage to the sendMessage method of the game
	 * controller and clears the playerMessage field.
	 */
	private void sendMessage() {
		gameController.sendMessage(playerMessage.getText());
		playerMessage.clear();
	}
}
