package com.holddie.mapreduce;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WeatherMapper extends Mapper<Text, Text, MyKey, DoubleWritable> {

	SimpleDateFormat sdf = new SimpleDateFormat();
	NullWritable nullWritable = NullWritable.get();
	@Override
	protected void map(Text key, Text value, Context context)
			throws IOException, InterruptedException {
		
		Date date;
		try {
			date = sdf.parse(key.toString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			
			double hot = Double.parseDouble(value.toString().substring(0, value.toString().lastIndexOf("c")));
			MyKey key2 = new MyKey();
			key2.setYear(year);
			key2.setMonth(month);
			key2.setHot(hot);
			context.write(key2, new DoubleWritable(hot));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}
