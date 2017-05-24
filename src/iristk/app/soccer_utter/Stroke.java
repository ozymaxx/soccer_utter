package iristk.app.soccer_utter;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class Stroke {
	private ArrayList<Point> points;
	private ArrayList<Line> lines;
	private int id;
	
	public Stroke(int id) {
		this.id = id;
		points = new ArrayList<Point>();
		lines = new ArrayList<Line>();
	}
	
	public void addPoint(double x,double y) {
		points.add(new Point(x,y));
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		int t = 0;
		for (Point pt : points) {
			builder.append(pt.toString());
			builder.append("\\t");
			builder.append(id);
			builder.append("\\t");
			builder.append(t++);
			builder.append("\\n");
		}
		
		return builder.toString();
	}
	
	public void reflectOnCanvas(Pane gc) {
		Line line;
		for (int i = 0; i < points.size()-1; ++i) {
			line = new Line();
			line.setStartX(points.get(i).getX());
			line.setStartY(points.get(i).getY());
			line.setEndX(points.get(i+1).getX());
			line.setEndY(points.get(i+1).getY());
			line.setStroke(Color.WHITE);
			line.setStrokeWidth(3);
			line.setStrokeLineCap(StrokeLineCap.ROUND);
			gc.getChildren().add(line);
			lines.add(line);
		}
	}
	
	public void emptyInUI(Pane gc) {
		for (Line line : lines) {
			gc.getChildren().remove(line);
		}
	}
}
