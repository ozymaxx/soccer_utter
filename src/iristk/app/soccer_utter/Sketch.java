package iristk.app.soccer_utter;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Sketch {
	private ArrayList<Stroke> strokes;
	private int strind;
	private int classification;
	private String teamAbbr;
	private String movementSemantic;
	private Text teamText;
	private Text movementSemanticText;
	
	public Sketch() {
		strokes = new ArrayList<Stroke>();
		strind = 0;
		classification = -1;
		teamAbbr = null;
		teamText = null;
		movementSemanticText = null;
		movementSemantic = null;
	}
	
	public void removeMovementSemantic(Pane root) {
		if (movementSemantic != null) {
			root.getChildren().remove(movementSemanticText);
			movementSemantic = null;
			movementSemanticText = null;
		}
	}
	
	public void putMovementSemantic(Pane root,String semantic) {
		removeMovementSemantic(root);
		Point centroid = getCentroid();
		movementSemantic = semantic;
		movementSemanticText = new Text(centroid.getX()-14,centroid.getY()+4,movementSemantic);
		movementSemanticText.setFont(new Font(18));
		movementSemanticText.setStroke(Color.AQUA);
		movementSemanticText.setFill(Color.AQUA);
		root.getChildren().add(movementSemanticText);
	}
	
	public void removeTeamName(Pane root) {
		if (teamAbbr != null) {
			root.getChildren().remove(teamText);
			teamAbbr = null;
			teamText = null;
		}
	}
	
	public void putTeamName(Pane root,String team) {
		removeTeamName(root);
		Point centroid = getCentroid();
		teamAbbr = Sketch.getAbbrOf(team);
		teamText = new Text(centroid.getX()-14,centroid.getY()+4,teamAbbr);
		teamText.setFont(new Font(18));
		teamText.setStroke(Color.DARKORANGE);
		teamText.setFill(Color.DARKORANGE);
		root.getChildren().add(teamText);
		//System.out.println(teamAbbr);
	}
	
	public static String getAbbrOf(String team) {
		String[] tokens = team.split(" ");
		
		if (tokens.length == 1) {
			return tokens[0].substring(0,3);
		}
		else if (tokens.length == 2) {
			return tokens[0].charAt(0)+""+tokens[1].substring(0,2);
		}
		else {
			return tokens[0].charAt(0)+""+tokens[1].charAt(0)+tokens[2].charAt(0);
		}
	}
	
	public int getClassification() {
		return classification;
	}
	
	public void setClassification(int classification) {
		this.classification = classification;
	}
	
	public void addPoint(double x,double y) {
		strokes.get(strokes.size()-1).addPoint(x, y);
	}
	
	public void openStroke() {
		strokes.add(new Stroke(strind++));
	}
	
	public Point getCentroid() {
		double sumx = 0,sumy = 0;
		double numpts = 0;
		
		for (Stroke str : strokes) {
			ArrayList<Point> points = str.getPoints();
			
			for (Point pt : points) {
				sumx += pt.getX();
				sumy += pt.getY();
				++numpts;
			}
		}
		
		return new Point(sumx/numpts,sumy/numpts);
	}
	
	public double getCoveringRadius() {
		Point cent = getCentroid();
		
		double radius = -1,currad;
		for (Stroke str : strokes) {
			ArrayList<Point> points = str.getPoints();
			
			for (Point pt : points) {
				currad = pt.distanceTo(cent);
				
				if (currad > radius) {
					radius = currad;
				}
			}
		}
		
		return radius;
	}
	
	public Point getStart() {
		return new Point(strokes.get(0).getPoints().get(0).getX(),strokes.get(0).getPoints().get(0).getY());
	}
	
	public Point getEnd() {
		double minX=1200,minY=700,maxX=-1,maxY=-1;
		
		for (Stroke str : strokes) {
			ArrayList<Point> points = str.getPoints();
			
			for (Point pt : points) {
				if (minX > pt.getX()) {
					minX = pt.getX();
				}
				
				if (minY > pt.getY()) {
					minY = pt.getY();
				}
				
				if (maxX < pt.getX()) {
					maxX = pt.getX();
				}
				
				if (maxY < pt.getY()) {
					maxY = pt.getY();
				}
			}
		}
		
		Point start = getStart();
		
		if (Math.abs(maxX-minX) <= 25) {
			if (Math.abs(start.getY()-maxY) <= Math.abs(start.getY()-minY)) {
				return new Point(start.getX(),minY);
			}
			else {
				return new Point(start.getX(),maxY);
			}
		}
		else if (Math.abs(maxY-minY) <= 25) {
			if (Math.abs(start.getX()-maxX) <= Math.abs(start.getX()-minX)) {
				return new Point(minX,start.getY());
			}
			else {
				return new Point(maxX,start.getY());
			}
		}
		else {
			Point southWest = new Point(minX,maxY);
			Point northWest = new Point(minX,minY);
			Point southEast = new Point(maxX,maxY);
			Point northEast = new Point(maxX,minY);
			
			if (southWest.distanceTo(start) <= 8) {
				return northEast;
			}
			else if (northWest.distanceTo(start) <= 8) {
				return southEast;
			}
			else if (southEast.distanceTo(start) <= 8) {
				return northWest;
			}
			else {
				return southWest;
			}
		}
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		for (Stroke str : strokes) {
			result.append(str.toString());
		}
		
		return result.toString();
	}
	
	public void reflectOnCanvas(Pane gc) {
		for (Stroke str : strokes) {
			str.reflectOnCanvas(gc);
		}
	}
	
	public void emptyInUI(Pane gc) {
		for (Stroke str : strokes) {
			str.emptyInUI(gc);
		}
	}
}
