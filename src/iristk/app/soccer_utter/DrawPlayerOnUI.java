package iristk.app.soccer_utter;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class DrawPlayerOnUI implements Runnable {
	
	Sketch symbol;
	Pane gc;
	boolean other;
	
	public DrawPlayerOnUI(Pane gc, Sketch symbol, boolean other) {
		this.gc = gc;
		this.symbol = symbol;
		this.other = other;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Ellipse ellipse = new Ellipse();
		Point centroid = symbol.getCentroid();
		ellipse.setCenterX(centroid.getX());
		ellipse.setCenterY(centroid.getY());
		ellipse.setRadiusX(25);
		ellipse.setRadiusY(25);
		ellipse.setStroke(Color.WHITE);
		
		if (!other) {
			ellipse.setFill(null);
		}
		else {
			ellipse.setFill(Color.WHITE);
		}
		
		ellipse.setStrokeWidth(3);
		symbol.emptyInUI(gc);
		gc.getChildren().add(ellipse);
	}

}
