package iristk.app.soccer_utter;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class DrawMovementOnUI implements Runnable {
	
	Sketch symbol;
	Pane gc;
	boolean ball;
	boolean closestFoundStart,closestFoundEnd;
	
	public DrawMovementOnUI(Pane gc, Sketch symbol, boolean ball,boolean f1,boolean f2) {
		this.gc = gc;
		this.symbol = symbol;
		this.ball = ball;
		closestFoundStart = f1;
		closestFoundEnd = f2;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Point movStart = symbol.getStart();
		Point movEnd = symbol.getEnd();
		Line line = new Line();
		line.setStartX(movStart.getX());
		line.setStartY(movStart.getY());
		line.setEndX(movEnd.getX());
		line.setEndY(movEnd.getY());
		line.setStrokeWidth(3);
		line.setStroke(Color.WHITE);
		line.setStrokeLineCap(StrokeLineCap.ROUND);
		
		if (!ball) {
			line.getStrokeDashArray().addAll(20d,20d);
		}
		
		symbol.emptyInUI(gc);
		gc.getChildren().add(line);
		
		double angle1 = Math.atan2(movEnd.getY()-movStart.getY(),movEnd.getX()-movStart.getX())-Math.PI/6;
		double angle2 = Math.atan2(movEnd.getY()-movStart.getY(),movEnd.getX()-movStart.getX())+Math.PI/6;
		double headd = 10;
		line = new Line();
		line.setStartX(movEnd.getX());
		line.setStartY(movEnd.getY());
		line.setEndX(movEnd.getX()-headd*Math.cos(angle1));
		line.setEndY(movEnd.getY()-headd*Math.sin(angle1));
		line.setStrokeWidth(3);
		line.setStroke(Color.WHITE);
		line.setStrokeLineCap(StrokeLineCap.ROUND);
		gc.getChildren().add(line);
		
		line = new Line();
		line.setStartX(movEnd.getX());
		line.setStartY(movEnd.getY());
		line.setEndX(movEnd.getX()-headd*Math.cos(angle2));
		line.setEndY(movEnd.getY()-headd*Math.sin(angle2));
		line.setStrokeWidth(3);
		line.setStroke(Color.WHITE);
		line.setStrokeLineCap(StrokeLineCap.ROUND);
		gc.getChildren().add(line);
		
		if (ball) {
			Ellipse ellipse;
			
			if (closestFoundStart) {
				ellipse = new Ellipse();
				ellipse.setCenterX(movStart.getX()+10);
				ellipse.setCenterY(movStart.getY()+10);
				ellipse.setRadiusX(5);
				ellipse.setRadiusY(5);
				ellipse.setStroke(Color.WHITE);
				ellipse.setFill(Color.BLACK);
				ellipse.setStrokeWidth(1);
				gc.getChildren().add(ellipse);
			}
			
			if (closestFoundEnd) {
				ellipse = new Ellipse();
				ellipse.setCenterX(movEnd.getX()-10);
				ellipse.setCenterY(movEnd.getY()-10);
				ellipse.setRadiusX(5);
				ellipse.setRadiusY(5);
				ellipse.setStroke(Color.WHITE);
				ellipse.setFill(Color.BLACK);
				ellipse.setStrokeWidth(1);
				gc.getChildren().add(ellipse);
			}
		}
	}

}
