package view;

import game.GameColor;

public class PatternSpacePane extends DiePane{

	private double size;
	private DiePane die;

	
	public PatternSpacePane(int eyes, GameColor color) {
		super(eyes, color);
	}
	
	public void setDie(int eyes, GameColor color) {
		die = new DiePane(eyes, color);
		getChildren().add(die);
		die.resize(size);
		die.setLayoutX(0);
		die.setLayoutY(0);
	}
	@Override
	public void resize(double size) {
		this.size = size;
		super.resize(size);;
	}

}
