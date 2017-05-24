package iristk.app.soccer_utter;

import java.util.List;
import java.io.File;
import iristk.xml.XmlMarshaller.XMLLocation;
import javafx.application.Platform;
import iristk.system.Event;
import iristk.flow.*;
import iristk.util.Record;
import static iristk.util.Converters.*;
import static iristk.flow.State.*;

public class Soccer_utterFlow extends iristk.flow.Flow {


	private void initVariables() {
	}

	@Override
	public Object getVariable(String name) {
		return null;
	}


	public Soccer_utterFlow() {
		initVariables();
	}

	@Override
	public State getInitialState() {return new Listen();}


	public class Listen extends State implements Initial {

		final State currentState = this;


		@Override
		public void setFlowThread(FlowRunner.FlowThread flowThread) {
			super.setFlowThread(flowThread);
		}

		@Override
		public void onentry() throws Exception {
			int eventResult;
			Event event = new Event("state.enter");
			// Line: 7
			try {
				EXECUTION: {
					int count = getCount(195600860) + 1;
					incrCount(195600860);
					iristk.flow.DialogFlow.listen state0 = new iristk.flow.DialogFlow.listen();
					if (!flowThread.callState(state0, new FlowEventInfo(currentState, event, new XMLLocation(new File("C:\\Users\\ozan\\IrisTK\\app\\soccer_utter\\src\\iristk\\app\\soccer_utter\\Soccer_utterFlow.xml"), 7, 12)))) {
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
				}
			} catch (Exception e) {
				throw new FlowException(e, currentState, event, new XMLLocation(new File("C:\\Users\\ozan\\IrisTK\\app\\soccer_utter\\src\\iristk\\app\\soccer_utter\\Soccer_utterFlow.xml"), 7, 12));
			}
		}

		@Override
		public int onFlowEvent(Event event) throws Exception {
			int eventResult;
			int count;
			// Line: 10
			try {
				count = getCount(997608398) + 1;
				if (event.triggers("sense.user.speak")) {
					incrCount(997608398);
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						// Line: 11
						int state = Soccer_utterSystem.drawFlow.getState();
						int side = Soccer_utterSystem.drawFlow.getPlayerSide();
						
						switch (state) {
							case StoryFlow.PLAYER_MARKED:
								if (((Record) event.get("sem")).get("team") != null && ((Record) event.get("sem")).get("pltype") != null) {
									System.out.println("Correct - team is "+((Record) event.get("sem")).get("team")+" and player type is "+((Record) event.get("sem")).get("pltype"));
									
									if (side == StoryFlow.PLAYER_SIDE_1) {
										if (Soccer_utterSystem.query.getTeamName1() != null) {
											if (!(((Record) event.get("sem")).get("team").toString().equals(Soccer_utterSystem.query.getTeamName1()))) {
												Platform.runLater(new PutTeamNames(((Record) event.get("sem")).get("team").toString(),Soccer_utterSystem.query.getTeamName1(),true,true));
											}
										}
										else {
											Platform.runLater(new PutTeamNames(((Record) event.get("sem")).get("team").toString(),null,true,true));
										}
									}
									else {
										if (Soccer_utterSystem.query.getTeamName2() != null) {
											if (!(((Record) event.get("sem")).get("team").toString().equals(Soccer_utterSystem.query.getTeamName2()))) {
												Platform.runLater(new PutTeamNames(Soccer_utterSystem.query.getTeamName2(),((Record) event.get("sem")).get("team").toString(),true,true));
											}
										}
										else {
											Platform.runLater(new PutTeamNames(null,((Record) event.get("sem")).get("team").toString(),true,true));
										}
									}
								}
								else if (((Record) event.get("sem")).get("team1") != null && ((Record) event.get("sem")).get("team2") != null) {
									System.out.println("Okay, "+((Record) event.get("sem")).get("team1")+" is playing "+((Record) event.get("sem")).get("team2"));
									boolean cond1 = ((Record) event.get("sem")).get("team1").toString().equals(Soccer_utterSystem.query.getTeamName1());
									boolean cond2 = ((Record) event.get("sem")).get("team2").toString().equals(Soccer_utterSystem.query.getTeamName2());
									
									if (!cond1 && !cond2) {
										Platform.runLater(new PutBothTeams(((Record) event.get("sem")).get("team1").toString(),((Record) event.get("sem")).get("team2").toString()));
									}
									else if (cond1 && Soccer_utterSystem.query.getTeamName2() == null) {
										Platform.runLater(new PutTeamNames(((Record) event.get("sem")).get("team1").toString(),((Record) event.get("sem")).get("team2").toString(),true,true));
									}
									else if (cond2 && Soccer_utterSystem.query.getTeamName1() == null) {
										Platform.runLater(new PutTeamNames(((Record) event.get("sem")).get("team1").toString(),((Record) event.get("sem")).get("team2").toString(),true,true));
									}
								}
								else if (((Record) event.get("sem")).get("minute") != null) {
									System.out.println("Okay, the match is in "+((Record) event.get("sem")).get("minute")+"th minute.");
									Platform.runLater(new SetTime(Integer.parseInt(((Record) event.get("sem")).get("minute").toString())));
								}
								else {
									System.out.println("Incorrect semantics after marking a player!");
								}
							break;
							case StoryFlow.BALL_MOVEMENT_MARKED:
								if (((Record) event.get("sem")).get("ballmov") != null) {
									System.out.println("Correct - ball movement is "+((Record) event.get("sem")).get("ballmov"));
									Platform.runLater(new PutIllustratedMovement(((Record) event.get("sem")).get("ballmov").toString()));
								}
								else if (((Record) event.get("sem")).get("team1") != null && ((Record) event.get("sem")).get("team2") != null) {
									System.out.println("Okay, "+((Record) event.get("sem")).get("team1")+" is playing "+((Record) event.get("sem")).get("team2"));
									Platform.runLater(new PutBothTeams(((Record) event.get("sem")).get("team1").toString(),((Record) event.get("sem")).get("team2").toString()));
								}
								else if (((Record) event.get("sem")).get("minute") != null) {
									System.out.println("Okay, the match is in "+((Record) event.get("sem")).get("minute")+"th minute.");
									Platform.runLater(new SetTime(Integer.parseInt(((Record) event.get("sem")).get("minute").toString())));
								}
								else {
									System.out.println("Incorrect semantics after marking a ball movement!");
								}
							break;
							case StoryFlow.PLAYER_MOVEMENT_MARKED:
								if (((Record) event.get("sem")).get("plmov") != null) {
									System.out.println("Correct - player movement is "+((Record) event.get("sem")).get("plmov"));
									Platform.runLater(new PutIllustratedMovement(((Record) event.get("sem")).get("plmov").toString()));
								}
								else if (((Record) event.get("sem")).get("team1") != null && ((Record) event.get("sem")).get("team2") != null) {
									System.out.println("Okay, "+((Record) event.get("sem")).get("team1")+" is playing "+((Record) event.get("sem")).get("team2"));
									Platform.runLater(new PutBothTeams(((Record) event.get("sem")).get("team1").toString(),((Record) event.get("sem")).get("team2").toString()));
								}
								else if (((Record) event.get("sem")).get("minute") != null) {
									System.out.println("Okay, the match is in "+((Record) event.get("sem")).get("minute")+"th minute.");
									Platform.runLater(new SetTime(Integer.parseInt(((Record) event.get("sem")).get("minute").toString())));
								}
								else {
									System.out.println("Incorrect semantics after marking a player movement!");
								}
							break;
							default:
								if (((Record) event.get("sem")).get("team1") != null && ((Record) event.get("sem")).get("team2") != null) {
									System.out.println("Okay, "+((Record) event.get("sem")).get("team1")+" is playing "+((Record) event.get("sem")).get("team2"));
									Platform.runLater(new PutBothTeams(((Record) event.get("sem")).get("team1").toString(),((Record) event.get("sem")).get("team2").toString()));
								}
								else if (((Record) event.get("sem")).get("minute") != null) {
									System.out.println("Okay, the match is in "+((Record) event.get("sem")).get("minute")+"th minute.");
									Platform.runLater(new SetTime(Integer.parseInt(((Record) event.get("sem")).get("minute").toString())));
								}
								else {
									System.out.println("Incorrect semantics!");
								}
						}
						// Line: 12
						flowThread.reentryState(this, new FlowEventInfo(currentState, event, new XMLLocation(new File("C:\\Users\\ozan\\IrisTK\\app\\soccer_utter\\src\\iristk\\app\\soccer_utter\\Soccer_utterFlow.xml"), 12, 15)));
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			} catch (Exception e) {
				throw new FlowException(e, currentState, event, new XMLLocation(new File("C:\\Users\\ozan\\IrisTK\\app\\soccer_utter\\src\\iristk\\app\\soccer_utter\\Soccer_utterFlow.xml"), 10, 36));
			}
			// Line: 14
			try {
				count = getCount(1174290147) + 1;
				if (event.triggers("sense.user.silence")) {
					incrCount(1174290147);
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						// Line: 15
						flowThread.reentryState(this, new FlowEventInfo(currentState, event, new XMLLocation(new File("C:\\Users\\ozan\\IrisTK\\app\\soccer_utter\\src\\iristk\\app\\soccer_utter\\Soccer_utterFlow.xml"), 15, 15)));
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			} catch (Exception e) {
				throw new FlowException(e, currentState, event, new XMLLocation(new File("C:\\Users\\ozan\\IrisTK\\app\\soccer_utter\\src\\iristk\\app\\soccer_utter\\Soccer_utterFlow.xml"), 14, 38));
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


}
