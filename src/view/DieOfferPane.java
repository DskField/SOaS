package view;

import java.util.ArrayList;

import game.Die;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DieOfferPane extends HBox {
	private final int squareSize = 70;
	private int trackSize;
	private ArrayList<Die>roundDies;
	
	public DieOfferPane(int intitialDieAmount, ArrayList<Die>roundDies) {
		trackSize = intitialDieAmount;
		setPrefSize(800, 50);
		this.roundDies = roundDies;
		
		addTrack();
		addDice();
		
	}
	private void addTrack() {
		for (int i = 0; i < trackSize; i++) {
			Rectangle rectangle = new Rectangle(squareSize, squareSize, squareSize, squareSize);
			rectangle.setFill(Color.BEIGE);
			getChildren().add(rectangle);
		}
	}
	private void addDice() {
		int counter = 0;
		for(Die die:roundDies) {
			counter +=1;
			System.out.println("addDice");
			int offset = (counter-squareSize)*trackSize;
			DiePane diePane = new DiePane(die.getDieId(), die.getDieValue(), die.getDieColor());
			diePane.setTranslateX(offset);
			diePane.setTranslateY(7.5);
			diePane.resize(55);
			getChildren().add(diePane);
			
		}
	}
}
