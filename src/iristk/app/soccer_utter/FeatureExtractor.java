package iristk.app.soccer_utter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import libsvm.svm_node;

public class FeatureExtractor {
	public static svm_node[] parseFeatStr(String featStr) {
		String[] featCh = featStr.substring(0,featStr.length()-1).substring(1).replace(" ", "").split(",");
		svm_node[] output = new svm_node[720];
		
		int i = 0;
		for (String ft : featCh) {
			output[i] = new svm_node();
			output[i].index = i;
			output[i].value = Double.parseDouble(ft);
			++i;
		}
		
		return output;
	}
	
	public static svm_node[] extract(String sketchJson) throws IOException {
		Runtime pyRuntime = Runtime.getRuntime();
		Process pyProcess = pyRuntime.exec("python -W ignore soccerretrieval_training/extract_feature.py "+sketchJson);
		BufferedReader processReader = new BufferedReader(new InputStreamReader(pyProcess.getInputStream()));
		
		String featStr;
		StringBuilder featbld = new StringBuilder();
		String line;
		
		while ((line = processReader.readLine()) != null) {
			featbld.append(line);
		}
		
		featStr = featbld.toString();
		svm_node[] output = parseFeatStr(featStr);
		
		return output;
	}
}
