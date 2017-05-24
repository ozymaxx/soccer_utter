package iristk.app.soccer_utter;

import java.io.IOException;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;

public class SymbolRecognizer {
	private svm_model model;
	private Classifier classifier;
	
	public SymbolRecognizer(String modelPath) throws IOException {
		model = svm.svm_load_model(modelPath);
		classifier = new Classifier(model);
	}
	
	public int classifySketch(String sketchJson) throws IOException {
		svm_node[] features = FeatureExtractor.extract(sketchJson);
		return classifier.classify(features);
	}
}
