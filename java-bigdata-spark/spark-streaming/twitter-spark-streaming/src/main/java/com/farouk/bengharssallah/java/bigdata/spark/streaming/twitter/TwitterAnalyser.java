package com.farouk.bengharssallah.java.bigdata.spark.streaming.twitter;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;

import scala.Tuple2;

import twitter4j.Status;
import twitter4j.auth.Authorization;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


         /** 
          *   {@link TwitterAnalyser} consumes data from twitter and printing the user and the body of the tweet
          *   written by an user in a fixed period (1 second in our case)
          */

public class TwitterAnalyser {

	public static void extract(){
		
				        // Prepare the spark configuration by setting application name and matser node "local" i.e. embedded mode
				        final SparkConf sparkConf = new SparkConf().setMaster("local[2]").setAppName("Twitter Data Processing");
				
				        // Create Streaming context using spark configuration and duration for which messages will be batched and fed to spark core
				        final JavaStreamingContext javaStreamingContext = new JavaStreamingContext(sparkConf, Duration.apply(1000));
				
				        // Prepare configuration for twitter authentication and authorization
				        final Configuration configuration = new ConfigurationBuilder().setDebugEnabled(false)
				                                                                      .setOAuthConsumerKey(" ")
				                                                                      .setOAuthConsumerSecret(" ")
				                                                                      .setOAuthAccessToken(" ")
				                                                                      .setOAuthAccessTokenSecret(" ")
				                                                                      .build();
				        // Create Twitter authorization object by passing prepared configuration containing consumer and access keys and tokens
				        final Authorization twitterAuthorization = new OAuthAuthorization(configuration);
				
				        // Create a data stream using streaming context and twitter authorization
				        final JavaReceiverInputDStream<Status> inputDStream = TwitterUtils.createStream(javaStreamingContext, twitterAuthorization, new String[]{});
				        				        				        
				        // Convert stream to pair stream with key as user screen name and value as tweet text
				        final JavaPairDStream<String, String> userTweetsStream = inputDStream.mapToPair(
				        		                                 (status) -> new Tuple2<String, String>(" Name :" + status.getUser().getScreenName(), "Tweet : " + status.getText())
				        		                                                                        );
				        	
				        // Group the tweets for each user
				        final JavaPairDStream<String, Iterable<String>> tweetsReducedByUser = userTweetsStream.groupByKey();
				        
				        // Iterate over the stream' RDDs and print each element on console	
				        tweetsReducedByUser.foreachRDD(pairRDD -> {
				        	          pairRDD.foreach(tuple ->  
								        	            	                 System.out.println("\n" + tuple._1 + "   " + tuple._2 +  " \n" ));
								        	                      
				                   });
				        
				        // Triggers the start of processing. Nothing happens if streaming context is not started
				        javaStreamingContext.start();
				        
				        // keeps the processing live by halting here unless terminated manually
				        javaStreamingContext.awaitTermination();
         }
	

    public static void main( String[] args )  {
    	                     // In Windows OS, download hadoop's winutils.exe and put in C:/Hadoop//bin folder 
    	                System.setProperty("hadoop.home.dir", "C:\\Hadoop\\");				                         
				        extract();
                   }

       }