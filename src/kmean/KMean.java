package kmean;

import java.sql.SQLException;
import java.util.ArrayList;

import data.Article;
import data.Cluster;
import data.Point;
import docWordMatrix.DocWordMatrixGenerator;
import recommendation.RecommendationModule;

public class KMean {
	private int NUM_CLUSTERS;
	private ArrayList<Cluster> clusters;
	private ArrayList<Article> articles;
	private static String userId = null;

	public static void main(String args[]) throws SQLException {
		long start = System.currentTimeMillis() ;
		if(args.length == 0) { 
			System.out.println("사용법: 추천받고자 하는 유저의 아이디 넣어라잉~!");
			return;
		}
		userId = args[0];
		DocWordMatrixGenerator generator = new DocWordMatrixGenerator(userId);
		generator.generate();
		System.out.println("***************************************************************");
		KMean articleKmean = new KMean(generator.getArticles(), (int) Math.floor(Math.sqrt((double) generator.getArticles().size() / 1.5)));
		articleKmean.init();
		articleKmean.calculate();
		articleKmean.printResult();
		System.out.println("***************************************************************");
		KMean userArticleKmean = new KMean(generator.getUserArticles());
		userArticleKmean.init();
		userArticleKmean.calculate();
		userArticleKmean.printResult();
		System.out.println("***************************************************************");
		RecommendationModule recommendationModule = new RecommendationModule(articleKmean.getClusters(), userArticleKmean.getClusters());
		recommendationModule.init();
		recommendationModule.recommend();
		long end = System.currentTimeMillis(); 
		System.out.println((end-start)/1000 +" 초 걸림");
	}

	public KMean(ArrayList<Article> articles, int numCluster) {
		this.clusters = new ArrayList<Cluster>();
		this.articles = articles;
		NUM_CLUSTERS = numCluster;
	}

	public KMean(ArrayList<Article> articles) {
		this.clusters = new ArrayList<Cluster>();
		this.articles = articles;
		NUM_CLUSTERS = (int) Math.floor(Math.sqrt((double) articles.size() / 2.0));
	}

	public void printResult() {
		double sse = 0;
		for(Cluster cluster : clusters) {
			System.out.println(cluster.toString() + "=> " + cluster.getSquaredError());
			sse += cluster.getSquaredError();
		}
		System.out.println("cluster 개수: " + clusters.size() + ", sse: " + sse/clusters.size());
	}

	public ArrayList<Cluster> getClusters() {
		return clusters;
	}

	public void init() {
		for(int i = 0; i < NUM_CLUSTERS; i++) {
			Cluster cluster = new Cluster(Integer.toString(i));
			Point centroid = new Point(articles.get(i).getPoint().getData());
			cluster.setCentroid(centroid);
			clusters.add(cluster);
		}
	}

	public void calculate() {
		boolean finish = false;

		while(!finish) {
			clearClusters();
			ArrayList<Point> lastCentroids = getCentroids();
			assignCluster();
			calcuateCentroid();
			ArrayList<Point> currentCentroids = getCentroids();
			double distance = 0;
			for(int i = 0; i < lastCentroids.size(); i++) {
				distance += Point.getDistance(lastCentroids.get(i), currentCentroids.get(i));
			}
			if(distance <= 0.0000001) {
				finish = true;
			}
		}
	}

	private void clearClusters() {
		for(Cluster cluster : clusters) {
			cluster.clear();
		}
	}

	private ArrayList<Point> getCentroids() {
		ArrayList<Point> centroids = new ArrayList<Point>(NUM_CLUSTERS);
		for(Cluster cluster : clusters) {
			Point centroid = cluster.getCentroid();
			Point point = new Point(centroid.getData());
			centroids.add(point);
		}
		return centroids;
	}

	private void assignCluster() {
		double distance  = 0.0;
		double min = 0;
		int clusterIndex = 0;

		for(Article article : articles) {
			min = Double.MAX_VALUE;
			for(int i = 0; i < NUM_CLUSTERS; i++) {
				Cluster cluster = clusters.get(i);
				distance = Point.getDistance(article.getPoint(), cluster.getCentroid());
				if(distance < min) {
					min = distance;
					clusterIndex = i;
				}
			}
			article.setCluster(clusters.get(clusterIndex));
			clusters.get(clusterIndex).addArticle(article);
		}
	}

	private void calcuateCentroid() {
		for(Cluster cluster : clusters) {
			ArrayList<Double> newCentroid = new ArrayList<Double>();
			for(int i = 0; i < cluster.getCentroid().getData().size(); i++) {
				double sum = 0;
				for(Article article : cluster.getArticles()) {
					sum += article.getPoint().getData().get(i);
				}
				newCentroid.add((sum/cluster.getArticles().size()));
			}
			cluster.setCentroid(new Point(newCentroid));
		}
	}
}
