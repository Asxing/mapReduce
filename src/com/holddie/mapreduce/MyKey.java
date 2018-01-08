package com.holddie.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class MyKey implements WritableComparable<MyKey>{

	private int year;
	private int month;
	private double hot;
	
	
	
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getHot() {
		return hot;
	}

	public void setHot(double hot) {
		this.hot = hot;
	}
	
	@Override
	public void readFields(DataInput arg0) throws IOException {
		this.year = arg0.readInt();
		this.month = arg0.readInt();
		this.hot = arg0.readDouble();
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		arg0.write(year);
		arg0.write(month);
		arg0.writeDouble(hot);
	}

	@Override
	public int compareTo(MyKey o) {
		int r1 = Integer.compare(this.year, o.getYear());
		if (r1==0) {
			int r2 = Integer.compare(this.month, o.getMonth());
			if (r2==0) {
				return Double.compare(this.hot, o.getHot());
			}else {
				return r2;
			}
		}else {
			return r1;
		}
	}
	
}
