package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginPane extends BorderPane{

	private HBox buttons;

	public LoginPane() {
		setPrefSize(500, 500);
		setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		createButtons();
		createTextFields();
	}

	public void createButtons() {
		buttons = new HBox();

		Button login = new Button("Login");
		login.setPrefSize(100, 50);
		login.setOnAction(e -> handleLogin());

		Button register = new Button("Registreer");
		register.setPrefSize(100, 50);
		register.setOnAction(e -> handleRegister());

		buttons.setSpacing(30);
		buttons.getChildren().addAll(login, register);
		buttons.setAlignment(Pos.CENTER);
	}

	public void createTextFields() {
		Label sagrada = new Label("Sagrada");
		sagrada.setStyle(" -fx-font-size:60px;-fx-text-fill: white");

		TextField username = new TextField();
		username.setPromptText("Gebruikersnaam");
		username.setAlignment(Pos.CENTER);

		PasswordField password = new PasswordField();
		password.setPromptText("Wachtwoord");
		password.setAlignment(Pos.CENTER);

		VBox textfields = new VBox();
		textfields.setSpacing(20);

		VBox fields = new VBox(username, password);
		fields.setSpacing(10);
		fields.setStyle("-fx-font-size:20px;");

		VBox butAndfields = new VBox(fields, buttons);
		butAndfields.setSpacing(10);
		butAndfields.setMinHeight(50);
		butAndfields.setMinWidth(200);
		butAndfields.setMaxWidth(200);
		textfields.setScaleX(1.3);
		textfields.setScaleY(1.3);

		textfields.getChildren().addAll(sagrada, butAndfields);
		textfields.setAlignment(Pos.CENTER);
		setCenter(textfields);
	}

	private void handleRegister() {

	}

	private void handleLogin() {

	}

}
