package org.productPackge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.core.Debug.Random;
import weka.core.Instances;

public class navebayestest {
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;

		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}

		return inputReader;
	}

	public static void main(String[] args) {
		try {
			CSV2Arff csv2arff = new CSV2Arff();
			boolean bool = csv2arff.convert("Sustainable.csv", "navfile.arff");

			NaiveBayesMultinomial nbm = new NaiveBayesMultinomial();

			if (bool) {
				BufferedReader datafile = readDataFile("navfile.arff");
				Instances data = new Instances(datafile);
				data.setClassIndex(data.numAttributes()-1);
			    Evaluation eval = new Evaluation(data);
			    eval.crossValidateModel(nbm, data, 10, new Random(1));
			    
			    System.out.println(eval.toSummaryString("\nResults\n======\n", false));
				
//				nbm.buildClassifier(data);
//				nbm.classifyInstance();

//				Map<Integer, List<Integer>> clusters = csv2arff.getClusters(assignments);
				
//				for(int cluster : clusters.keySet()){
//					System.out.println("Cluster : " +cluster);
//					for(int val : clusters.get(cluster)){
//						System.out.print(val + " ");
//					}
//					System.out.println("\n\n");
//				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

