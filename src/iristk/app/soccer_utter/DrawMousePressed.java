package iristk.app.soccer_utter;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DrawMousePressed implements EventHandler<MouseEvent>{
	private Pane gc;
	private MotionStory ms;
	
	public DrawMousePressed(Pane gc,MotionStory ms) {
		this.gc = gc;
		this.ms = ms;
	}

	@Override
	public void handle(MouseEvent event) {
		// TODO Auto-generated method stub
		if (Soccer_utterSystem.timerRunning) {
			Soccer_utterSystem.newSketchTimer.cancel();
			Soccer_utterSystem.timerRunning = false;
		}
		
		ms.openStroke();
		ms.addPoint(event.getSceneX(), event.getSceneY());
		ms.reflectOnCanvas(gc);
	}

}
