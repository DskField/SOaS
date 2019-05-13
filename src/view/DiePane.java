package view;

import game.GameColor;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DiePane extends Pane {
	private Circle[] circles;
	//	private GameColor color;

	private double circleSize;
	private double circleRadius;
	private double spacing;

	private double size;

	private int eyes;

	public DiePane(int eyes, GameColor color) {
		this.eyes = eyes;
		size = 79;

		circleSize = size * 0.2;
		circleRadius = circleSize / 2;
		spacing = (size - (circleSize * 3)) / 4;

		addCircles();

		setBackground(new Background(new BackgroundFill(color.getColor(), null, null)));
		setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		setMinSize(size, size);
		setMaxSize(size, size);
	}

	public void resize(double size) {
		this.size = size - 1;

		circleSize = this.size * 0.2;
		circleRadius = circleSize / 2;
		spacing = (this.size - (circleSize * 3)) / 4;

		getChildren().clear();
		addCircles();

		setMinSize(this.size, this.size);
		setMaxSize(this.size, this.size);
	}

	private void addCircles() {
		circles = new Circle[7];

		circles[0] = new Circle(spacing + circleRadius, spacing + circleRadius, circleRadius, Color.BLACK);
		circles[1] = new Circle(spacing * 3 + circleSize * 2 + circleRadius, spacing + circleRadius, circleRadius, Color.BLACK);
		circles[2] = new Circle(spacing + circleRadius, spacing * 2 + circleSize + circleRadius, circleRadius, Color.BLACK);
		circles[3] = new Circle(spacing * 2 + circleSize + circleRadius, spacing * 2 + circleSize + circleRadius, circleRadius, Color.BLACK);
		circles[4] = new Circle(spacing * 3 + circleSize * 2 + circleRadius, spacing * 2 + circleSize + circleRadius, circleRadius, Color.BLACK);
		circles[5] = new Circle(spacing + circleRadius, spacing * 3 + circleSize * 2 + circleRadius, circleRadius, Color.BLACK);
		circles[6] = new Circle(spacing * 3 + circleSize * 2 + circleRadius, spacing * 3 + circleSize * 2 + circleRadius, circleRadius, Color.BLACK);

		switch (eyes) {
		case 1:
			getChildren().addAll(circles[3]);
			break;
		case 2:
			getChildren().addAll(circles[0], circles[6]);
			break;
		case 3:
			getChildren().addAll(circles[0], circles[3], circles[6]);
			break;
		case 4:
			getChildren().addAll(circles[0], circles[1], circles[5], circles[6]);
			break;
		case 5:
			getChildren().addAll(circles[0], circles[1], circles[3], circles[5], circles[6]);
			break;
		case 6:
			getChildren().addAll(circles[0], circles[1], circles[2], circles[4], circles[5], circles[6]);
			break;
		default:
			break;
		}
	}
}
