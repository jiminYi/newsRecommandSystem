package fuzzyEm;

import java.util.ArrayList;

import data.Article;
import data.Cluster;
import data.Point;

public class FuzzyEm {
	private ArrayList<Article> articles;
	private ArrayList<Cluster> clusters;
	private double[][] m;
	private Point previousPoint[] = new Point[2];
	private int dimension = 5;

	public FuzzyEm(ArrayList<Article> articles) {
		this.articles = articles;
	}

	public void init() {
		clusters = new ArrayList<Cluster>(2);
		clusters.add(getNewCluster(new Point(articles.get(0).getPoint())));
		for(int i = 0; i < articles.size(); i++) {
			if(!articles.get(i).getPoint().sim(articles.get(0).getPoint())) {
				clusters.add(getNewCluster(new Point(articles.get(i).getPoint())));
				break;
			}
		}
		m = new double[2][articles.size()];
	}

	public void start() {
		if(clusters.size() == 1) {
			return;
		}
		while(!isEnough()) {
			setNewM();
			setNewCentroid();
		}
		matchArticleCluster();
	}

	private boolean isEnough() {
		double sum1 = 0, sum2 = 0;
		if(previousPoint[0] == null || previousPoint[1] == null) {
			return false;
		}
		sum1 = Math.pow(Point.getDistance(previousPoint[0], clusters.get(0).getCentroid()), 2); 
		sum2 = Math.pow(Point.getDistance(previousPoint[1], clusters.get(1).getCentroid()), 2); 
		if((Math.sqrt(sum1) < 0.0001) && (Math.sqrt(sum2) < 0.0001)) {
			return true;
		} else {
			return false;
		}
	}

	private void matchArticleCluster() {
		for(Article article : articles) {
			double distance[] = new double[2];
			for(int i = 0; i < 2; i++) {
				double dist = Point.getDistance(article.getPoint(), clusters.get(i).getCentroid());
				distance[i] = dist;
			}
			if(distance[0] < distance[1]) {
				article.setCluster(clusters.get(0));
				clusters.get(0).addArticle(article);
			} else {
				article.setCluster(clusters.get(1));
				clusters.get(1).addArticle(article);
			}
		}
	}

	private void setNewCentroid() {
		for(int i = 0; i < 2; i++) {
			Point newCentroid = new Point();
			for(int j = 0; j < dimension; j++) {
				double sumOfSquares = 0;
				double sumOfSquareMult = 0;
				for(int k = 0; k < articles.size(); k++) {
					double squaredValue = Point.round(Math.pow(m[i][k], 2));
					sumOfSquares += squaredValue;
					sumOfSquareMult += Point.round((Point.round(articles.get(k).getPoint().getData().get(j)) * squaredValue));
				}
				newCentroid.addData(sumOfSquareMult / sumOfSquares);
			}
			previousPoint[i] = clusters.get(i).getCentroid();
			clusters.get(i).setCentroid(newCentroid);
		}
	}

	private void setNewM() {
		for(int i = 0; i < articles.size(); i++) {
			double sum = 0;
			double dist1 = Point.getDistance(clusters.get(0).getCentroid(), articles.get(i).getPoint());
			double dist2 = Point.getDistance(clusters.get(1).getCentroid(), articles.get(i).getPoint());
			sum = dist1 + dist2;
			m[0][i] = Point.getDistance(clusters.get(1).getCentroid(), articles.get(i).getPoint()) / sum;
			m[1][i] = Point.getDistance(clusters.get(0).getCentroid(), articles.get(i).getPoint()) / sum;
		}
	}

	private Cluster getNewCluster(Point newCentroid) {
		return new Cluster(newCentroid);
	}

	public void printResult() {
		System.out.println("cluster 1:");
		System.out.println("개수: " + clusters.get(0).getArticles().size());
		System.out.println("cluster 2:");
		System.out.println("개수: " + clusters.get(1).getArticles().size());
	}

	public ArrayList<Cluster> getClusters() {
		return clusters;
	}

}