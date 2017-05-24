package iristk.app.soccer_utter;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;

public class Classifier {
	private svm_model model;
	
	public Classifier(svm_model model) {
		this.model = model;
	}
	
	public int classify(svm_node[] features) {
		return (int) (svm.svm_predict(model, features));
	}
}
