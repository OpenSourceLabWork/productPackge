package org.productPackge;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class CSV2Arff {
	/**
	 * takes 2 arguments: - CSV input file - ARFF output file
	 */
	public boolean convert(String input, String output) throws Exception {

		// load CSV
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(input));
		Instances data = loader.getDataSet();

		// save ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(output));
		//saver.setDestination(new File(output));
		saver.writeBatch();

		return true;
	}
	
	public Map<Integer, List<Integer>> getClusters(int[] assignments){
		
		Map<Integer, List<Integer>> map  = new HashMap<Integer, List<Integer>>();
		int i = 0;
		List<Integer> list ;
		for (int clusterNum : assignments) {
			if (!map.containsKey(clusterNum)) {
				list = new ArrayList<Integer>();
				list.add(i);
				map.put(clusterNum, list);
			} else {
				List <Integer> mlist =  map.get(clusterNum);
				mlist.add(i);
				map.put(clusterNum, mlist);
			}
			i++;
		}
		return map;
	}
}