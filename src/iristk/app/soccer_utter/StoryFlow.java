package iristk.app.soccer_utter;

public class StoryFlow {
	public static final int PLAYER_MARKED = 1;
	public static final int BALL_MOVEMENT_MARKED = 2;
	public static final int PLAYER_MOVEMENT_MARKED = 3;
	public static final int TIMER_INDICATED = 4;
	public static final int SIDES_INDICATED = 5;
	
	public static final int PLAYER_SIDE_1 = 6;
	public static final int PLAYER_SIDE_2 = 7;
	
	int lastState,side;
	
	public StoryFlow() {
		lastState = -1;
		side = 1;
	}
	
	public void updateState(int state) {
		lastState = state;
	}
	
	public void notifyPlayerUpdated(int side) {
		this.side = side;
	}
	
	public int getPlayerSide() {
		return side;
	}
	
	public int getState() {
		return lastState;
	}
	
	public void evaluateSemantics() {
		return;
	}
}
