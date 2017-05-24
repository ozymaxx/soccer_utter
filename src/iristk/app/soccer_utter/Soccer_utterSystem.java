/*******************************************************************************
 * Copyright (c) 2014 Gabriel Skantze.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Gabriel Skantze - initial API and implementation
 ******************************************************************************/
package iristk.app.soccer_utter;

import iristk.speech.OpenVocabularyContext;
import iristk.speech.SemanticGrammarContext;
import iristk.speech.SpeechGrammarContext;
import iristk.speech.Voice.Gender;
import iristk.speech.google.GoogleRecognizerFactory;
import iristk.speech.nuance9.NuanceRecognizerFactory;
import iristk.speech.nuancecloud.NuanceCloudRecognizerFactory;
import iristk.speech.windows.WindowsRecognizerFactory;
import iristk.speech.windows.WindowsSynthesizer;
import iristk.system.IrisUtils;
import iristk.system.SimpleDialogSystem;
import iristk.util.Language;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Timer;

import iristk.cfg.SRGSGrammar;
import iristk.flow.FlowModule;

public class Soccer_utterSystem extends Application {
	public static Timer newSketchTimer;
	public static boolean timerRunning;
	public static SymbolRecognizer sr;
	public static SearchQuery query;
	public static StoryFlow drawFlow;
	public static Stage mainStage;

	public Soccer_utterSystem() throws Exception {
		// Create the system
		SimpleDialogSystem system = new SimpleDialogSystem(this.getClass());
		
		// Uncomment this if you want to turn on logging
		//system.setupLogging(new File("c:/iristk_logging"), true);
		
		// Set up the GUI
		//system.setupGUI();
		
		// Add the recognizer to the system
		system.setupRecognizer(new GoogleRecognizerFactory());
		
		// Set the language of the system
		system.setLanguage(Language.TURKISH);
		
		// Add a synthesizer to the system		
		//system.setupSynthesizer(new WindowsSynthesizer(), Gender.FEMALE);
		
		// Add the flow
		system.addModule(new FlowModule(new Soccer_utterFlow()));
		
		// Load a grammar in the recognizer
		system.loadContext("default", new SemanticGrammarContext(new SRGSGrammar(system.getPackageFile("new_grammar.xml"))));
		system.loadContext("open", new OpenVocabularyContext(Language.TURKISH));
		
		// Start the interaction
		system.sendStartSignal();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			mainStage = primaryStage;
			Platform.setImplicitExit(false);
			drawFlow = new StoryFlow();
			sr = new SymbolRecognizer("soccerretrieval_training/recognizer.model");
			
			MotionStory ms = new MotionStory();
			newSketchTimer = new Timer();
			timerRunning = false;
			
			Button showQuery = new Button();
			showQuery.setText("SHOW QUERY");
			
			showQuery.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					System.out.println(Soccer_utterSystem.query.toString());
				}
				
			});
			
			Pane root = new Pane();
			root.setPrefWidth(1140);
			root.setPrefHeight(650);
			root.setStyle("-fx-background-image:url('"+getClass().getResource("soccerfield.png").toExternalForm()+"')");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			showQuery.setId("show-query");
			root.setOnMousePressed(new DrawMousePressed(root,ms));
			root.setOnMouseDragged(new DrawMouseDragged(root,ms));
			root.setOnMouseReleased(new DrawMouseReleased(root,ms));
			//root.getChildren().add(showQuery);
			primaryStage.setTitle("Soccer Search");
			primaryStage.setResizable(false);
			query = new SearchQuery(root,ms);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		launch(args);
		new Soccer_utterSystem();
	}

}
