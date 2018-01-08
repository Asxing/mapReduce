package com.holddie.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class RunJob {

	public static void main(String[] args) {
		Configuration config = new Configuration();
		config.set("fs.defaultFS", "hdfs://holddie137:8020");
		config.set("yarn.resourcemanager.hostname", "holddie137");
		
		try {
			FileSystem fs = FileSystem.get(config);
			
			Job job = Job.getInstance();
			job.setJarByClass(RunJob.class);
			job.setJobName("weather");
			job.setMapperClass(WeatherMapper.class);
			job.setReducerClass(WeatherReducer.class);
			job.setMapOutputKeyClass(MyKey.class);
			job.setMapOutputValueClass(DoubleWritable.class);
			job.setPartitionerClass(MyPartitioner.class);
			job.setSortComparatorClass(MySort.class);
			job.setGroupingComparatorClass(MyGroup.class);
			job.setNumReduceTasks(3);
			job.setInputFormatClass(KeyValueTextInputFormat.class);
			
			FileInputFormat.addInputPath(job, new Path("/input/weather"));
			Path outPath = new Path("/output/weather");
			if (fs.exists(outPath)) {
				fs.delete(outPath,true);
			}
			FileOutputFormat.setOutputPath(job, outPath);
			
			boolean f = job.waitForCompletion(true);
			if (f) {
				System.out.println("执行成功！");
			}
			
			
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
