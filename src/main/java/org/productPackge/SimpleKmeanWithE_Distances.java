package org.productPackge;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;

public class SimpleKmeanWithE_Distances {

	public static void main(String[] args) throws Exception {
		try {
			CSV2Arff csv2arff = new CSV2Arff();
			boolean bool = csv2arff.convert("servicedata.csv", "anies.arff");
			SimpleKMeans skm = new SimpleKMeans();
			skm.setSeed(10);
			skm.setPreserveInstancesOrder(true);
			skm.setNumClusters(3);

			if (bool) {
				BufferedReader datafile = KmeanTest.readDataFile("anies.arff");
				Instances data = new Instances(datafile);
				skm.buildClusterer(data);
				ClusterEvaluation eval = new ClusterEvaluation();
				eval.setClusterer(skm);
				EuclideanDistance Dist = (EuclideanDistance) skm.getDistanceFunction();
				Instances clusterCentroid = skm.getClusterCentroids();
				int[] assignments = skm.getAssignments();

				for ( int i = 0; i < clusterCentroid.numInstances(); i++ ) {
				    Instance inst = clusterCentroid.instance( i );
				    double value = inst.value( 0 );
				    System.out.println( "Centroid " + i + ": " + value );
				}
				System.out.println("\n\n");

				Map<Integer, List<Integer>> clusters = csv2arff.getClusters(assignments);
				
				for(int cluster : clusters.keySet()){
					System.out.println("Cluster : " +cluster);
					for(int val : clusters.get(cluster)){
						System.out.print(val + " ");
					}
					System.out.println("\n\n");
				}
				
				for (int iI = 0; iI < data.numInstances(); iI++) {
					int j = iI + 1;

					System.out.println("Data1: " + data.instance(iI));
					for (int k = 0; k < clusterCentroid.size(); k++) {
						Double dist1 = Dist.distance(clusterCentroid.get(k), data.instance(iI));
						System.out.println("Data " + j + " Distance from Centriod " + k + ": " + dist1);
					}
				}
			}
		} catch (Exception ex) {

		}
	}
}
