package view;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RoundPane extends Pane {
	private final int squareSize = 80;
	private final int trackSize = 10;

	private int x;
	private int y;

	private HashMap<Integer, ArrayList<DiePane>> roundTrack;

	public RoundPane(int x, int y) {
		this.x = x;
		this.y = y;

		roundTrack = new HashMap<Integer, ArrayList<DiePane>>();

		for (int i = 1; i <= trackSize; i++) {
			roundTrack.put(i, new ArrayList<DiePane>());
		}

		addTrack();
	}

	private void addTrack() {
		for (int i = 0; i < trackSize; i++) {
			Rectangle rectangle = new Rectangle(x + i * squareSize, y, squareSize, squareSize);
			rectangle.setStroke(Color.BLACK);
			rectangle.setFill(Color.ALICEBLUE);
			getChildren().add(rectangle);
		}
	}

	private void addDice() {
		for (int i = 1; i <= trackSize; i++) {
			ArrayList<DiePane> currentRound = roundTrack.get(i);
			int offset1 = (i - 1) * squareSize;
			if (currentRound.size() == 1) {
				DiePane diePane = currentRound.get(0);
				diePane.setTranslateX(x + offset1);
				diePane.setTranslateY(y);
				getChildren().add(currentRound.get(0));
			} else if (currentRound.size() <= 4) {
				for (int j = 0; j < currentRound.size(); j++) {
					double newSquareSize = squareSize / 2;
					DiePane diePane = currentRound.get(j);
					diePane.setTranslateX((j % 2 == 0) ? x + offset1 : x + offset1 + newSquareSize);
					diePane.setTranslateY((j <= 1) ? y : y + newSquareSize);
					diePane.resize(newSquareSize);
					getChildren().add(diePane);
				}
			} else if (currentRound.size() <= 9) {
				for (int j = 0; j < currentRound.size(); j++) {
					double newSquareSize = squareSize / 3;
					double offset2 = j % 3 * newSquareSize;
					DiePane diePane = currentRound.get(j);
					diePane.setTranslateX((j % 3 == 0) ? x + offset1 : x + offset1 + offset2);
					diePane.setTranslateY((j <= 2) ? y : (j <= 5) ? y + newSquareSize : y + newSquareSize * 2);
					diePane.resize(newSquareSize);
					getChildren().add(diePane);
				}
			}
		}
	}

	private void update() {
		getChildren().clear();
		addTrack();
		addDice();
	}

	//Temporary
	public void addDie(Integer key, DiePane diePane) {
		roundTrack.get(key).add(diePane);
		update();
	}
}
