package com.farouk.bengharssallah.java.bigdata.spark.machinelearning.clustering.kmeans;

import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.SparkConf;

public class Kmeans {
	
	
			    public static void main( String[] args ) {
			    	
			    	SparkConf conf = new SparkConf().setAppName("K-means Example");
			        JavaSparkContext context = new JavaSparkContext(conf);
			
			        String path = "/home/prateek/Desktop/NSIT_marks.txt";
			        JavaRDD<String> data = context.textFile(path);
			        JavaRDD<Vector> parsedData = data.map(new Function<String, Vector>() {
			        	
							            public Vector call(String s) {
							              String[] sarray = s.split(" ");
							              double[] values = new double[sarray.length];
							              for (int i = 0; i < sarray.length; i++) {
							            	  values[i] = Double.parseDouble(sarray[i]);
							              }
							              return Vectors.dense(values);
							            }
							          }
			            );
			        
			        parsedData.cache();
			
			        int numberOfClusters = 4;
			        int numberOfIterations = 20;
			        
			        KMeansModel clusters = KMeans.train(parsedData.rdd(), numberOfClusters, numberOfIterations);
			
			        double WSSSE = clusters.computeCost(parsedData.rdd());
			        System.out.println("Within Set Sum of Squared Errors = " + WSSSE);
			        
			        for (Vector center : clusters.clusterCenters()) {
			                                    System.out.println(" " + center);
			                        }
			        context.stop();
			          }
      }