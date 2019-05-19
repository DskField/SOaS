package view;

import game.GameColor;
import game.Player;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class GlassWindowPane extends BorderPane {
	private final int glassWindowWidth = 400;
	private final int glassWindowHeight = 800;
	private final int spacingAmount = 20;

	private FieldPane fieldPane;

	private StackPane scoreField;
	private CornerRadii windowCurve;
	private GameColor color;
	private Circle circle;
	private Label labelScore;
	private Label labelName;

	private int switchingNumber;

	private boolean isSmall = false;

	public GlassWindowPane(int number, Player player, GameScene gameScene) {
		switchingNumber = number;
		this.color = player.getColor();

		fieldPane = new FieldPane(player.getGlassWindow().getPatternCard());
		fieldPane.loadGlassWindow(player.getGlassWindow());

		windowCurve = new CornerRadii(5, 3, 3, 5, 0, 0, 0, 0, true, true, true, true, false, false, false, false);

		setBackground(new Background(new BackgroundFill(Color.rgb(68, 47, 25), windowCurve, null)));
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, windowCurve, new BorderWidths(5))));

		setScore();
		setName(player.getUsername());
		setBottom(fieldPane);

		setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (isSmall) {
					gameScene.switchGlassWindows(switchingNumber);
				}
			}
		});

		resize();
	}

	public void toggleIsSmall() {
		isSmall = !isSmall;
		resize();
	}

	private void resize() {
		if (isSmall) {
			setPrefSize(glassWindowWidth / 2, glassWindowHeight / 2.5);
			setMaxSize(glassWindowWidth / 2, glassWindowHeight / 2.5);
			setMargin(fieldPane, new Insets(10));
			circle.setRadius(60);
			labelScore.setFont(Font.font(20));
			labelName.setFont(Font.font(20));
			setMargin(scoreField, new Insets(10, 20, 0, 20));
		} else {
			setPrefSize(glassWindowWidth, glassWindowHeight);
			setMaxSize(glassWindowWidth, glassWindowHeight);
			circle.setRadius(140);
			labelScore.setFont(Font.font(40));
			labelName.setFont(Font.font(40));
			setMargin(scoreField, new Insets(spacingAmount));
			setMargin(fieldPane, new Insets(spacingAmount));
		}

		fieldPane.resize(isSmall);
	}

	public void updateScore(int score) {
		labelScore.setText(String.valueOf(score));
	}

	private void setName(String name) {
		labelName = new Label(name);
		labelName.setTextFill(Color.WHITE);
		labelName.setFont(Font.font(40));

		setCenter(labelName);
	}

	private void setScore() {
		scoreField = new StackPane();
		labelScore = new Label();
		labelScore.setTextFill(Color.BLACK);
		labelScore.setFont(Font.font(40));

		circle = new Circle(180, 180, 140);
		circle.setFill(color.getColor());
		circle.setStroke(Color.BLACK);

		setMargin(scoreField, new Insets(spacingAmount));

		scoreField.getChildren().addAll(circle, labelScore);
		setTop(scoreField);
	}

	public void setSwitchingNumber(int num) {
		switchingNumber = num;
	}

	public GameColor getColor() {
		return color;
	}
}
