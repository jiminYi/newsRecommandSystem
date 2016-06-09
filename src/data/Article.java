package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Article implements Serializable {
	private Point point;
	private String url;
	private String title;
	private ArrayList<String> keyword;
	private Cluster cluster;
	private double distance;
	
	public Article(String url) {
		this.url = url;
	}
	
	public Article(String url, String title, String keyword) {
		super();
		this.url = url;
		this.title = title;
		this.keyword = new ArrayList<String>();
		String[] temp = keyword.split(" ");
		for(int i = 0; i < 10 && i < temp.length; i++) {
			this.keyword.add(temp[i]);
		}
	}

	public Article(String url, String keyword) {
		super();
		this.url = url;
		this.keyword = new ArrayList<String>();
		String[] temp = keyword.split(" ");
		for(int i = 0; i < 10 && i < temp.length; i++) {
			this.keyword.add(temp[i]);
		}
	}

	@Override
	public boolean equals(Object obj) {
		Article article = (Article) obj;
		if(this.getUrl().equals(article.getUrl())) {
			return true;
		} else{
			return false;
		}
	}

	@Override
	public String toString() {
		return "Article [url=" + url + ", title=" + title + "\n point=" + point.toString() + "]";
	}
	
	public Article(Point point) {
		super();
		this.point = point;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public void setPoint(Point point) {
		this.point = point;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public ArrayList<String> getKeyword() {
		return keyword;
	}
	
	public void setKeyword(ArrayList<String> keyword) {
		this.keyword = keyword;
	}
	
	public Cluster getCluster() {
		return cluster;
	}
	
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
