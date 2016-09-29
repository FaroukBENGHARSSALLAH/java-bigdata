package com.farouk.bengharssallah.java.bigdata.spark.machinelearning.classification.spamdetector;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.regression.LabeledPoint;

import org.apache.spark.sql.DataFrame;

public class SpamDetector{
	

		public static void main(String[] args) {
		
			            System.setProperty("hadoop.home.dir", "C:\\Hadoop\\");
	        
		                  // Define a configuration to use to interact with Spark
                        SparkConf conf = new SparkConf().setMaster("local").setAppName("svm mlib");
                         
                          // Create a Java version of the Spark Context from the configuration
					    JavaSparkContext sc = new JavaSparkContext(conf);
						
						
						//load 2types of email from text files : spam and ham(nom-spam)
						//Each line has text from one email
						JavaRDD spam = sc.textFile("data/spam");
						JavaRDD ham = sc.textFile("data/ham");
								
						//create HashingTF instance to map email text to vectors of 100 features
						final HashingTF tf = new HashingTF();
						tf.setNumFeatures(100);
						
						//Each email is split into words, and each word is mapped to one feature
						//Create LabeledPoint datasets for posive(spam) and negative(ham) examples.
						
						
						JavaRDD positiveExamples = spam.map(new Function<String,LabeledPoint> () {
						
								   @Override
								public LabeledPoint call(String email) throws Exception {
											return new LabeledPoint(1, (Vector) tf.(new DataFrame(Arrays.asList(email.split(" "))));
								                     }
						           });
						
						
						JavaRDD negativeExamples = ham.map(new Function<String,LabeledPoint> () {
							
							   @Override
							public LabeledPoint call(String email) throws Exception {
										return new LabeledPoint(0, (Vector) tf.transform((DataFrame) Arrays.asList(email.split(" "))));
							                     }
					              });
						
						JavaRDD<LabeledPoint> trainingData = positiveExamples.union(negativeExamples);
						      // cache data since Logistic Regression is an iterative algorithm
						trainingData.cache();
						
						    // create a Logistic Regression learner which uses the LBFGS optimiser
						LogisticRegressionWithSGD lrLearner = new LogisticRegressionWithSGD();
						
						    // Run the actual learning algorithm on the training data.
						LogisticRegressionModel model = lrLearner.run(trainingData.rdd());
						
						
						   // Test on apositive example (spam) and a neagative one (ham).
						   // First apply the same HashingTF feature transforamtion used on the training data.
						
						 Vector posTestExample = 
								 (Vector) tf.transform((DataFrame) Arrays.asList("O M G GET cheap stuff by sending money to ....".split(" ")));
						 Vector negTestExample = 
								 (Vector) tf.transform((DataFrame) Arrays.asList("Hi Dad, I started studying spark on the other ....".split(" ")));;						
						
						// Now use the learned model to predict spam/ham new emails.
                         System.out.println("Prediction for positive test example : " + model.predict(posTestExample));
                         System.out.println("Prediction for negative test example : " + model.predict(negTestExample));

						sc.stop();
		            }
        }