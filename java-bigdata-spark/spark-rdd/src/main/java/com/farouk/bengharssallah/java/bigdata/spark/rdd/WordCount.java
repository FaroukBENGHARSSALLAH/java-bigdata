package com.farouk.bengharssallah.java.bigdata.spark.rdd;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

		/**
		 * 
		 * {@link WordCount} class parse an input text file and count every word
		 *
 */ 
public class WordCount {

	public static void wordCount( String filename ){
		
				        // Define a configuration to use to interact with Spark
				        SparkConf sparkConfiguration = new SparkConf().setMaster("local").setAppName("Work Count App");
				
				        // Create a Java version of the Spark Context from the configuration
				        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConfiguration);
				
				        // Load the input data, which is a text file read from the command line
				        JavaRDD<String> input = javaSparkContext.textFile( filename );
				
				        // Java 8 with lambdas: split the input string into words
				        JavaRDD<String> words = input.flatMap(s -> Arrays.asList( s.split(" ") ) );
				
				        // Java 8 with lambdas: transform the collection of words into pairs (word and 1) and then count them
				        JavaPairRDD<String, Integer> counts = words.mapToPair(w -> new Tuple2<String, Integer>(w, 1)).reduceByKey((x, y) -> x + y);
				
				        // Save the word count back out to a text file, causing evaluation.
				        counts.saveAsTextFile( "output" );
				        
				          // close spark context
				        javaSparkContext.close();
         }
	

    public static void main( String[] args )  {
    	
    	                     // In Windows OS, download hadoop's winutils.exe and put in C:/Hadoop//bin folder
    	                System.setProperty("hadoop.home.dir", "C:\\Hadoop\\");
    	                
				        if( args.length == 0 ) {
								            System.out.println( "Usage: WordCount <file>" );
								            System.exit( 0 );
				                          }
                         wordCount( args[ 0 ] );
                   }

       }