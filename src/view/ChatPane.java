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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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

	private MyButtonHandler myButtonHandler;
	private MyMessageSendHandler myMessageSendHandler;

	private GameController gameController;

	public ChatPane(GameController gameController) {
		//setting the GameController
		this.gameController = gameController;

		//handles the makeup of the ChatPane
		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
		setPrefSize(chatPaneWidth, chatPaneheight);

		//creates the Chat
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
		myButtonHandler = new MyButtonHandler();
		myMessageSendHandler = new MyMessageSendHandler();

		// Handles makeup
		sendMessage.setPrefSize(sendButtonWidth, sendButtonHeight);
		sendMessage.setOnAction(myButtonHandler);
		playerMessage.setPrefWidth(chatPaneWidth - sendButtonWidth);
		playerMessage.setOnKeyPressed(myMessageSendHandler);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(center);

		// sets everything to the ChatPane
		bottom.getChildren().addAll(playerMessage, sendMessage);
		setCenter(scrollPane);
		setBottom(bottom);
	}

	/**
	 * adds Messages to the Chat.
	 * 
	 * @param messages - ArrayList<Message> this list of Messages will be added to the chat
	 */
	public void updateChat(ArrayList<Message> messages) {
		for (Message message : messages) {
			center.getChildren().add(new MessagePane(message.getUserName(), message.getMessage(), message.getChatTime()));
		}
		scrollPane.vvalueProperty().bind(center.heightProperty());
	}

	/**
	 * calls upon the sendMessage method when the "Verzenden" button is pressed.
	 *
	 */
	private class MyButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			sendMessage();
		}
	}

	/**
	 * calls upon the sendMessage method when the enter key is pressed.
	 *
	 */
	private class MyMessageSendHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER) {
				sendMessage();
			}
		}
	}

	/**
	 * Gives the the String from playerMessage to the sendMessage method of the game controller and
	 * clears the playerMessage field.
	 */
	private void sendMessage() {
		gameController.sendMessage(playerMessage.getText());
		playerMessage.clear();
	}
}
