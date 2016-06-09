package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Point implements Serializable {
	private ArrayList<Double> data;

	public Point(ArrayList<Double> data) {
		super();
		this.data = data;
	}

	public Point(Point point) {
		data = new ArrayList<Double>();
		for(Double d : point.getData()) {
			data.add(d);
		}
	}

	public Point() {
		data = new ArrayList<Double>();
	}

	public void addData(double d) {
		data.add(d);
	}

	public ArrayList<Double> getData() {
		return data;
	}

	public void setData(ArrayList<Double> data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object obj) {
		Point other = (Point) obj;
		boolean isEqual = true;
		for(int i = 0; i < 5; i++) {
			if(this.getData().get(i) - other.getData().get(i) != 0) {
				isEqual = false;
				break;
			} else if(this.getData().get(i) == other.getData().get(i)) {
			}
		}
		return isEqual;
	}
	
	public boolean sim(Object obj) {
		Point other = (Point) obj;
		boolean isEqual = true;
		for(int i = 0; i < 5; i++) {
			if(this.getData().get(i) - other.getData().get(i) > 0.0001) {
				isEqual = false;
				break;
			} else if(this.getData().get(i) == other.getData().get(i)) {
			}
		}
		return isEqual;
	}

	//	public static double getDistance(Point p1, Point p2) {
	//		double distance = 0;
	//		double a = 0, b = 0, c = 0;
	//		int dimension = p1.getData().size();
	//		if(p2.getData().size() != dimension) {
	//			return -1;
	//		}
	//		for(int i = 0; i < dimension; i++) {
	//			a += p1.getData().get(i) * p2.getData().get(i);
	//			b += Math.pow(p1.getData().get(i), 2);
	//			c += Math.pow(p2.getData().get(i), 2);
	//		}
	//		if(a == 0) {
	//			return 1;
	//		} else {
	//			distance = (a / (Math.sqrt(b*c)));
	//			return 1 - distance;
	//		}
	//	}

	public static double getDistance(Point p1, Point p2) {
		double distance = 0;
		double sum = 0;
		for(int i = 0; i < p1.getData().size(); i++) {
			sum += Point.round(Math.pow(p2.getData().get(i) - p1.getData().get(i), 2));
		}
		distance = Point.round(Math.sqrt(sum));
		return distance;
	}

	@Override
	public String toString() {
		String data = "";
		for(Double d : this.data) {
			data += d + " ";
		}
		return "Point [data=" + data + "]";
	}

	public static double round(double d) {
		String str = String.format("%.10f", d);
		return Double.parseDouble(str);
	}
}
