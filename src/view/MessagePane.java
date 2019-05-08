package view;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MessagePane extends HBox {
	private Label name;
	private Label message;
	private Label timestamp;
	
	public MessagePane(String username, String text, String time) {
		name = new Label(username + ": ");
		message = new Label(text);
		message.setWrapText(true);
		message.setPrefWidth(450);
		timestamp = new Label(" -" + time);
		
		
		getChildren().addAll(name, message, timestamp);
	}

}
