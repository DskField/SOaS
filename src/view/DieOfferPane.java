package view;

import java.util.ArrayList;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DieOfferPane extends HBox {
	private final int squareSize = 70;
	private final int backgroundWidth = 630;
	private final int backgroundHeight = 70;
	
	private int trackSize;
	private ArrayList<DiePane> roundDies;

	public DieOfferPane() {
		trackSize = 9;
		roundDies = new ArrayList<>();

		addTrack();

	}

	private void addTrack() {
		Rectangle rectangle = new Rectangle(backgroundWidth, backgroundHeight);
		rectangle.setFill(Color.BEIGE);
		getChildren().add(rectangle);

	}

	private void addDice() {
		int counter = 0;
		for (DiePane diePane : roundDies) {
			counter += 1;
			int offset = (counter - squareSize) * trackSize;
			diePane.setTranslateX(offset);
			diePane.setTranslateY(7.5);
			diePane.resize(55);
			getChildren().add(diePane);

		}
	}
	public void update(DiePane diePane) {
		roundDies.clear();
		roundDies.add(diePane);
		addDice();
	}
}
