package iristk.app.soccer_utter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class NewSketchTask extends TimerTask {
	public static final int PLAYER_MOVEMENT = 0;
	public static final int BALL_MOVEMENT = 1;
	public static final int PLAYER_POSITION = 2;
	public static final int OTHER_PLAYER_POSITION = 3;
	public static String[] type = {"player movement","ball movement","player position","other player position"};
	private MotionStory ms;
	private Pane gc;
	
	public NewSketchTask(MotionStory ms,Pane gc) {
		this.ms = ms;
		this.gc = gc;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.print("Classified as... ");
		
		try {
			int classification = Soccer_utterSystem.sr.classifySketch(ms.latestSymbol().toString())-1; 
			//System.out.print(type[classification]);
			double rad,xseg,yseg;
			Point centroid;
			Line line;
			Ellipse ellipse;
			
			switch (classification) {
			case PLAYER_MOVEMENT:
				Point plmovStart = ms.latestSymbol().getStart();
				Point plmovEnd = ms.latestSymbol().getEnd();
				ArrayList<Sketch> sketches = ms.getSymbols();
				
				double minDist = 20000;
				Point closestPlayerCenter = null;
				int classified = -1;
				double cur;
				
				for (Sketch symbol : sketches) {
					if (symbol.getClassification() == PLAYER_POSITION || symbol.getClassification() == OTHER_PLAYER_POSITION) {
						cur = symbol.getCentroid().distanceTo(plmovStart);
						
						if (cur < minDist) {
							minDist = cur;
							closestPlayerCenter = symbol.getCentroid();
							classified = symbol.getClassification();
						}
					}
				}
				
				if (minDist <= 100) {
					int t = 1;
					
					if (classified == OTHER_PLAYER_POSITION) {
						t = 2;
					}
					
					Soccer_utterSystem.query.addMovement(new PlayerMovement(t,closestPlayerCenter,plmovEnd));
					/*ellipse = new Ellipse();
					ellipse.setCenterX(closestPlayerCenter.getX());
					ellipse.setCenterY(closestPlayerCenter.getY());
					ellipse.setRadiusX(16);
					ellipse.setRadiusY(16);
					ellipse.setStroke(Color.AQUA);
					ellipse.setStrokeWidth(6);
					ellipse.setFill(null);
					gc.getChildren().add(ellipse);*/
				}
				else {
					Soccer_utterSystem.query.addMovement(new PlayerMovement(-1,plmovStart,plmovEnd));
				}
				
				ms.latestSymbol().setClassification(classification);
				Platform.runLater(new DrawMovementOnUI(gc,ms.latestSymbol(),false,false,false));
				
				Soccer_utterSystem.drawFlow.updateState(StoryFlow.PLAYER_MOVEMENT_MARKED);
				//System.out.println(" (from "+ms.latestSymbol().getStart().getX()+","+ms.latestSymbol().getStart().getY()+" to "+ms.latestSymbol().getEnd().getX()+","+ms.latestSymbol().getEnd().getY()+")");
				break;
			case BALL_MOVEMENT:
				Point movStart = ms.latestSymbol().getStart();
				Point movEnd = ms.latestSymbol().getEnd();
				ArrayList<Sketch> symbols = ms.getSymbols();
				
				double minDistStart = 20000, minDistEnd = 20000;
				Point closestPlayerCenterStart = null,closestPlayerCenterEnd = null;
				int classificationStart = -1,classificationEnd = -1;
				double curDist;
				boolean f1 = false, f2 = false;
				
				for (Sketch symbol : symbols) {
					if (symbol.getClassification() == PLAYER_POSITION || symbol.getClassification() == OTHER_PLAYER_POSITION) {
						curDist = symbol.getCentroid().distanceTo(movStart);
						
						if (curDist < minDistStart) {
							minDistStart = curDist;
							closestPlayerCenterStart = symbol.getCentroid();
							classificationStart = symbol.getClassification();
						}
						
						curDist = symbol.getCentroid().distanceTo(movEnd);
						
						if (curDist < minDistEnd) {
							minDistEnd = curDist;
							closestPlayerCenterEnd = symbol.getCentroid();
							classificationEnd = symbol.getClassification();
						}
					}
				}
				
				if (minDistStart <= 100 && minDistEnd <= 100) {
					int t1 = 1;
					int t2 = 1;
					f1 = f2 = true;
					
					if (classificationStart == OTHER_PLAYER_POSITION) {
						t1 = 2;
					}
					
					if (classificationEnd == OTHER_PLAYER_POSITION) {
						t2 = 2;
					}
					
					Soccer_utterSystem.query.addMovement(new BallMovement(t1,t2,closestPlayerCenterStart,closestPlayerCenterEnd));
					/*line = new Line();
					line.setStartX(closestPlayerCenterStart.getX()-8);
					line.setStartY(closestPlayerCenterStart.getY()-8);
					line.setEndX(closestPlayerCenterStart.getX()+8);
					line.setEndY(closestPlayerCenterStart.getY()+8);
					line.setStroke(Color.DARKORANGE);
					line.setStrokeLineCap(StrokeLineCap.ROUND);
					line.setStrokeWidth(6);
					gc.getChildren().add(line);
					line = new Line();
					line.setStartX(closestPlayerCenterEnd.getX()-8);
					line.setStartY(closestPlayerCenterEnd.getY()-8);
					line.setEndX(closestPlayerCenterEnd.getX()+8);
					line.setEndY(closestPlayerCenterEnd.getY()+8);
					line.setStroke(Color.DARKORANGE);
					line.setStrokeLineCap(StrokeLineCap.ROUND);
					line.setStrokeWidth(6);
					gc.getChildren().add(line);*/
				}
				else if (minDistStart <= 100) {
					int t = 1;
					f1 = true;
					
					if (classificationStart == OTHER_PLAYER_POSITION) {
						t = 2;
					}
					
					Soccer_utterSystem.query.addMovement(new BallMovement(t,-1,closestPlayerCenterStart,movEnd));
					/*line = new Line();
					line.setStartX(closestPlayerCenterStart.getX()-8);
					line.setStartY(closestPlayerCenterStart.getY()-8);
					line.setEndX(closestPlayerCenterStart.getX()+8);
					line.setEndY(closestPlayerCenterStart.getY()+8);
					line.setStroke(Color.DARKORANGE);
					line.setStrokeLineCap(StrokeLineCap.ROUND);
					line.setStrokeWidth(6);
					gc.getChildren().add(line);*/
				}
				else if (minDistEnd <= 100) {
					int t = 1;
					f2 = true;
					
					if (classificationEnd == OTHER_PLAYER_POSITION) {
						t = 2;
					}
					
					Soccer_utterSystem.query.addMovement(new BallMovement(-1,t,movStart,closestPlayerCenterEnd));
					/*line = new Line();
					line.setStartX(closestPlayerCenterEnd.getX()-8);
					line.setStartY(closestPlayerCenterEnd.getY()-8);
					line.setEndX(closestPlayerCenterEnd.getX()+8);
					line.setEndY(closestPlayerCenterEnd.getY()+8);
					line.setStroke(Color.DARKORANGE);
					line.setStrokeWidth(6);
					line.setStrokeLineCap(StrokeLineCap.ROUND);
					gc.getChildren().add(line);*/
				}
				else {
					Soccer_utterSystem.query.addMovement(new BallMovement(-1,-1,movStart,movEnd));
				}
				
				ms.latestSymbol().setClassification(classification);
				Platform.runLater(new DrawMovementOnUI(gc,ms.latestSymbol(),true,f1,f2));
				
				Soccer_utterSystem.drawFlow.updateState(StoryFlow.BALL_MOVEMENT_MARKED);
				//System.out.println(" (from "+ms.latestSymbol().getStart().getX()+","+ms.latestSymbol().getStart().getY()+" to "+ms.latestSymbol().getEnd().getX()+","+ms.latestSymbol().getEnd().getY()+")");
				break;
			case PLAYER_POSITION:
				Soccer_utterSystem.query.addPlayer(ms.latestSymbol().getCentroid(), 1);
				ms.latestSymbol().setClassification(classification);
				Platform.runLater(new DrawPlayerOnUI(gc, ms.latestSymbol(), false));
				Soccer_utterSystem.drawFlow.updateState(StoryFlow.PLAYER_MARKED);
				Soccer_utterSystem.drawFlow.notifyPlayerUpdated(StoryFlow.PLAYER_SIDE_1);
				
				if (Soccer_utterSystem.query.getTeamName1() != null && Soccer_utterSystem.query.getSidesCertain()) {
					Platform.runLater(new PutSingleTeamName(ms.latestSymbol(),gc,Soccer_utterSystem.query.getTeamName1()));
				}
				
				//System.out.println(" at "+ms.latestSymbol().getCentroid().getX()+","+ms.latestSymbol().getCentroid().getY()+")");
				break;
			default:
				Soccer_utterSystem.query.addPlayer(ms.latestSymbol().getCentroid(), 2);
				ms.latestSymbol().setClassification(classification);
				Platform.runLater(new DrawPlayerOnUI(gc, ms.latestSymbol(), true));
				Soccer_utterSystem.drawFlow.updateState(StoryFlow.PLAYER_MARKED);
				Soccer_utterSystem.drawFlow.notifyPlayerUpdated(StoryFlow.PLAYER_SIDE_2);
				
				if (Soccer_utterSystem.query.getTeamName2() != null && Soccer_utterSystem.query.getSidesCertain()) {
					Platform.runLater(new PutSingleTeamName(ms.latestSymbol(),gc,Soccer_utterSystem.query.getTeamName2()));
				}
				
				//System.out.println(" at "+ms.latestSymbol().getCentroid().getX()+","+ms.latestSymbol().getCentroid().getY()+")");
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ms.newSketch();
		Soccer_utterSystem.newSketchTimer.cancel();
		Soccer_utterSystem.timerRunning = false;
	}
}
