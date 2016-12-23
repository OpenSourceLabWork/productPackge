package org.productPackge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Hello world!
 *
 */
public class KmeanTest 
{
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
			boolean bool = csv2arff.convert("servicedata.csv", "anies.arff");
			SimpleKMeans kmeans = new SimpleKMeans();
			kmeans.setSeed(10);
			kmeans.setPreserveInstancesOrder(true);
			kmeans.setNumClusters(3);
			
			if (bool) {
				BufferedReader datafile = readDataFile("anies.arff");
				Instances data = new Instances(datafile);
				 
				 
				kmeans.buildClusterer(data);
		
				int[] assignments = kmeans.getAssignments();
				Instances instances = kmeans.getClusterCentroids();
				
//			    printing centriods
				for ( int i = 0; i < instances.numInstances(); i++ ) {
				    Instance inst = instances.instance( i );
				    double value = inst.value( 0 );
				    System.out.println( "Centroid " + i + ": " + value );
				}

				Map<Integer, List<Integer>> clusters = csv2arff.getClusters(assignments);
				
				for(int cluster : clusters.keySet()){
					System.out.println("Cluster : " +cluster);
					for(int val : clusters.get(cluster)){
						System.out.print(val + " ");
					}
					System.out.println("\n\n");
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}