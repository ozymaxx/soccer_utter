package iristk.app.soccer_utter;

import java.util.Timer;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DrawMouseReleased implements EventHandler<MouseEvent> {
	private Pane gc;
	private MotionStory ms;
	
	public DrawMouseReleased(Pane gc,MotionStory ms) {
		this.gc = gc;
		this.ms = ms;
	}

	@Override
	public void handle(MouseEvent event) {
		// TODO Auto-generated method stub
		Soccer_utterSystem.newSketchTimer = new Timer();
		Soccer_utterSystem.newSketchTimer.schedule(new NewSketchTask(ms,gc), 1000);
		Soccer_utterSystem.timerRunning = true;
		ms.addPoint(event.getSceneX(), event.getSceneY());
		ms.reflectOnCanvas(gc);
	}

}
