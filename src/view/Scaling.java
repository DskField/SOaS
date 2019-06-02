package view;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Scaling {
	final private static double standard = 2000;
	final private static Rectangle2D bounds = Screen.getPrimary().getBounds();
	
	final private static double width = bounds.getWidth();
	final private static double height = bounds.getHeight();
	
	public static double getWidthScaling(double size) {
		return (size / standard) * width;
	}
	
	public static double getHeightScaling(double size) {
		return (size / standard) * height;
	}
}
