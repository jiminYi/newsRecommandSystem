package recommendation;

import java.util.ArrayList;

import data.Article;
import data.Cluster;
import data.Point;
import sort.ClusterDistanceCompare;
import sort.ClusterSizeCompare;

public class RecommendationModule {
	ArrayList<Cluster> clusters;
	ArrayList<Cluster> userClusters;
	ArrayList<String> result;
	
	public RecommendationModule(ArrayList<Cluster> clusters, ArrayList<Cluster> userClusters) {
		this.clusters = clusters;
		this.userClusters = userClusters;	
	}
	
	public void init() {
		userClusters.sort(new ClusterSizeCompare());
	}
	
	public void recommend() {
		result = new ArrayList<String>();
		for(Cluster userCluster : userClusters) {
			userCluster.sortArticle(userCluster.getCentroid());
			getRecommendedClusters(userCluster);
			setResult(userCluster);
		}
	}
	
	public ArrayList<String> getResult() {
		return result;
	}
	
	private void setResult(Cluster userCluster) {
		System.out.println("=================================================");
		System.out.println(userCluster.toString());
		System.out.println("추천 리스트: ");
		clusters.get(0).sortArticle(userCluster.getCentroid());
		for(Article article : clusters.get(0).getArticles()) {
			result.add(article.getUrl());
			result.add(article.getTitle());
			//System.out.println(article.getUrl() + ", " + article.getTitle());
		}
		result.add("@@");
	}
	
//	private void printResult(Cluster userCluster) {
//		System.out.println("=================================================");
//		System.out.println(userCluster.toString());
//		System.out.println("추천 리스트: ");
//		clusters.get(0).sortArticle(userCluster.getCentroid());
//		System.out.println(clusters.get(0).toString());
//		System.out.println(Point.getDistance(userCluster.getCentroid(), clusters.get(0).getCentroid()));
//	}

	public ArrayList<Cluster> getRecommendedClusters(Cluster userCluster) {
		Point userCentroid = userCluster.getCentroid();
		
		for(int i = 0; i < clusters.size(); i++) {
			Cluster cluster = clusters.get(i);
			double distance = Point.getDistance(userCentroid, cluster.getCentroid());
			cluster.setDistance(distance);
		}
		clusters.sort(new ClusterDistanceCompare());
		return clusters;
	}
}
