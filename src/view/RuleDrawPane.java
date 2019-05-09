package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

class RuleDrawPane extends Pane {
	// constants
	private final int paneWidth = 800;
	private final int paneHeight = 750;

	// instance variables
	private ImageView imageView;

	// constructor
	public RuleDrawPane() {
		setPrefSize(paneWidth, paneHeight);
		setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		imageView = new ImageView();

		imageView.fitWidthProperty().bind(widthProperty());
		imageView.fitHeightProperty().bind(heightProperty());
		imageView.setPreserveRatio(true);

		StackPane stackPaneWrapper = new StackPane(imageView);
		stackPaneWrapper.prefWidthProperty().bind(widthProperty());
		stackPaneWrapper.prefHeightProperty().bind(heightProperty());

		getChildren().add(stackPaneWrapper);

	}

	public void showProgression() {
		imageView.setImage(new Image("file:Resources/images/Spelverloop.png"));
	}

	public void showDiePlacing() {
		imageView.setImage(new Image("file:Resources/images/Dobbelsteenplaatsen.png"));
	}

	public void showToolCard() {
		imageView.setImage(new Image("file:Resources/images/Gereedschapskaarten.png"));
	}
}
