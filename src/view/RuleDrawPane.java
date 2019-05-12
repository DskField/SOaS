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
	private final int PANEWIDTH = 800;
	private final int PANEHEIGHT = 750;

	// instance variables
	private ImageView imageView;

	// constructor
	public RuleDrawPane() {
		setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		imageView = new ImageView();

		// this make the images look good on the pane
		setPrefHeight(PANEHEIGHT);
		setMinHeight(PANEHEIGHT);
		setMaxHeight(PANEHEIGHT);
		setPrefWidth(PANEWIDTH);
		setMinWidth(PANEWIDTH);
		setMaxWidth(PANEWIDTH);

		StackPane stackPaneWrapper = new StackPane(imageView);
		stackPaneWrapper.prefWidthProperty().bind(widthProperty());
		stackPaneWrapper.prefHeightProperty().bind(heightProperty());

		getChildren().add(stackPaneWrapper);

	}

//show rules about progression
	public void showProgression() {
		imageView.setImage(new Image("file:Resources/images/Spelverloop.png"));
	}

	// show rules about die placing
	public void showDiePlacing() {
		imageView.setImage(new Image("file:Resources/images/Dobbelsteenplaatsen.png"));
	}

//show rules about toolcards
	public void showToolCard() {
		imageView.setImage(new Image("file:Resources/images/Gereedschapskaarten.png"));
	}
}
