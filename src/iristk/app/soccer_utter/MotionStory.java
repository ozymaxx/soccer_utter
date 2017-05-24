package iristk.app.soccer_utter;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class MotionStory {
	private ArrayList<Sketch> symbols;
	
	public MotionStory() {
		symbols = new ArrayList<Sketch>();
		symbols.add(new Sketch());
	}
	
	public void addPoint(double x,double y) {
		symbols.get(symbols.size()-1).addPoint(x, y);
	}
	
	public void openStroke() {
		symbols.get(symbols.size()-1).openStroke();
	}
	
	public Sketch latestSymbol() {
		return symbols.get(symbols.size()-1);
	}
	
	public ArrayList<Sketch> getSymbols() {
		return symbols;
	}
	
	public void newSketch() {
		symbols.add(new Sketch());
	}
	
	public Sketch getLatestMovementInProperCase() {
		if (symbols.size() >= 2) {
			return symbols.get(symbols.size()-2);
		}
		else {
			return null;
		}
	}
	
	public void reflectOnCanvas(Pane gc) {
		for (Sketch symbol : symbols) {
			if (symbol.getClassification() < 0) {
				symbol.reflectOnCanvas(gc);
			}
		}
	}
	
	public void removeTeamNames(Pane gc) {
		for (Sketch symbol : symbols) {
			if (symbol.getClassification() == NewSketchTask.PLAYER_POSITION || symbol.getClassification() == NewSketchTask.OTHER_PLAYER_POSITION) {
				symbol.removeTeamName(gc);
			}
		}
	}
	
	public void putTeamNames(Pane gc,String team1,String team2) {
		for (Sketch symbol : symbols) {
			if (symbol.getClassification() == NewSketchTask.PLAYER_POSITION) {
				if (team1 != null) {
					symbol.putTeamName(gc, team1);
				}
			}
			else if (symbol.getClassification() == NewSketchTask.OTHER_PLAYER_POSITION) {
				if (team2 != null) {
					symbol.putTeamName(gc, team2);
				}
			}
		}
	}
}
