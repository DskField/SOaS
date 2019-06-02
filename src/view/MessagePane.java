package view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class MessagePane extends BorderPane {
	/* CONSTANTS */
	private final int messageWidth = 280;

	/* VARIABLES */
	private Label name;
	private Label message;
	private Label timestamp;

	public MessagePane(String username, String text, String time) {
		// initialized name label
		name = new Label(username + ": ");

		//initialize message label and handels makeup
		message = new Label(text);
		message.setWrapText(true);
		message.setPrefWidth(messageWidth);

		//initialize timestamp label
		timestamp = new Label(" -" + time);

		//adds everything to the MessagePane
		setLeft(name);
		setCenter(message);
		setRight(timestamp);
	}

}
