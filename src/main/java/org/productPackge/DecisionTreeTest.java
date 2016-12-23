package org.productPackge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.sql.rowset.Predicate;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;

public class DecisionTreeTest {
	
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
	public static Evaluation classify(Classifier model,
			Instances trainingSet, Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);
 
		model.buildClassifier(trainingSet);
		evaluation.evaluateModel(model, testingSet);
 
		return evaluation;
	}
 
	@SuppressWarnings("deprecation")
	public static double calculateAccuracy(FastVector predictions) {
		double correct = 0;
 
		for (int i = 0; i < predictions.size(); i++) {
			NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
			if (np.predicted() == np.actual()) {
				correct++;
			}
		}
 
		return 100 * correct / predictions.size();
	}
 
	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
 
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
 
		return split;
	}

	public static void main(String[] args) throws Exception {
//		
//		CSV2Arff csv2arff = new CSV2Arff();
//		boolean bool = csv2arff.convert("Sustainable.csv", "anies.arff");
//		if (bool) {
		BufferedReader datafile = readDataFile("TESTINGFINALUPDATED.arff");
 
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
 
		// Do 10-split cross validation
		Instances[][] split = crossValidationSplit(data, 10);
 
		// Separate split into training and testing arrays
		Instances[] trainingSplits = split[0];
		Instances[] testingSplits = split[1];
 
		// Use a set of classifiers
		Classifier model =	new J48();
		
		FastVector predictions = new FastVector();
		 
		// For each training-testing split pair, train and test the classifier
		for (int i = 0; i < trainingSplits.length; i++) {
			Evaluation validation = classify(model, trainingSplits[i], testingSplits[i]);

			predictions.appendElements(validation.predictions());
			
		}
		for(int i=0; i<predictions.size(); i++){
			System.out.println(predictions.get(i)+"	");
		}
		
		// Calculate overall accuracy of current classifier on all splits
		double accuracy = calculateAccuracy(predictions);

		// Print current classifier's name and accuracy in a complicated,
		// but nice-looking way.
		System.out.println("Accuracy of " + model.getClass().getSimpleName() + ": "
				+ String.format("%.2f%%", accuracy)
				+ "\n---------------------------------");
		}
//	}
}
