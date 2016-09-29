package com.farouk.bengharssallah.java.bigdata.hadoop.mapreduce;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

        /**
         * 
         * {@link WordCount} class parse an input text file and count every word
         *
         */     

public class WordCount {

	
			 /**
		     * 
		     * {@link Map} write a tuple for every word a single word, word : WORD =>  (WORD, 1)
		     *
		     */  
	
	
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
						private final static IntWritable one = new IntWritable(1);
						private Text word = new Text();
				
						 /**
					     * 
					     * for each line encapsulated in value variable, we split words by blank spaces and writes them as tuples
					     *
					     */
						
						public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
									String line = value.toString();
									StringTokenizer tokenizer = new StringTokenizer(line);
									while (tokenizer.hasMoreTokens()) {
										word.set(tokenizer.nextToken());
										output.collect(word, one);
									}
						 }
	         }
	
	

	
			  /**
			     * 
			     * {@link Reduce} calculate for every word, its total's '1'   (WORD, 1), (WORD, 1), (WORD, 1) => (WORD, 3)
			     *
			     */ 
	
	
	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
		
				 /**
			     * 
			     * after beign grouped by key, this method count the number of the tuples
			     *
			     */
						public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
									int sum = 0;
									while (values.hasNext()) {
										           sum += values.next().get();
									          }
									output.collect(key, new IntWritable(sum));
						}
	          }
	

	public static void main(String[] args) throws Exception {
		                        
		                         // In Windows OS, download hadoop's winutils.exe and put in C:/Hadoop//bin folder 
						System.setProperty("hadoop.home.dir", "C:\\Hadoop\\");
						            //configure Hadoop's MapReduce
						JobConf jobConfiguration = new JobConf(WordCount.class);
						
						jobConfiguration.setJobName("wordcount");
				            // Key Type, text in our case
						jobConfiguration.setOutputKeyClass(Text.class);
						    // key associated value, int in our case
						jobConfiguration.setOutputValueClass(IntWritable.class);
				
						    // mapper class
						jobConfiguration.setMapperClass(Map.class);
						   // combiner class
						jobConfiguration.setCombinerClass(Reduce.class);
						   // reducer class
						jobConfiguration.setReducerClass(Reduce.class);
				              // input format, text in our case
						jobConfiguration.setInputFormat(TextInputFormat.class);
						     // output format, text in our case
						jobConfiguration.setOutputFormat(TextOutputFormat.class);
				               // input file to be parsed
						FileInputFormat.setInputPaths(jobConfiguration, new Path(args[0]));
						       // output folder
						FileOutputFormat.setOutputPath(jobConfiguration, new Path(args[1]));
				                  // start the MapReduce
						JobClient.runJob(jobConfiguration);
						
	                     }
            }
