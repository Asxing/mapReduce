package com.holddie.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WeatherReducer extends Reducer<MyKey, DoubleWritable, Text, NullWritable> {

	@Override
	protected void reduce(MyKey arg0, Iterable<DoubleWritable> arg1,
			Context arg2) throws IOException, InterruptedException {
		
		int i = 0;
		for (DoubleWritable doubleWritable : arg1) {
			i++;
			String msg = (arg0.getYear()+1) +"\t"+(arg0.getMonth()+1)+"\t"+doubleWritable.get();
			arg2.write(new Text(msg), NullWritable.get());
			if (i>=3) {
				break;
			}
		}
		
	}
}
