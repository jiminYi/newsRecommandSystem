package fuzzyEm;

import java.util.ArrayList;

import data.Article;
import data.Cluster;

public class FuzzyEmController {
	private ArrayList<Cluster> clusters;
	private double beforeError;
	private double afterError;
	private boolean isFirstIter = true;

	public FuzzyEmController(ArrayList<Article> articles) {
		Cluster initCluster = new Cluster();
		for(Article article : articles) {
			initCluster.addArticle(article);
			article.setCluster(initCluster);
		}
		clusters = new ArrayList<Cluster>();
		clusters.add(initCluster);
	}

	public void clustering() {
		while(!isEnough() || isFirstIter) {
			beforeError = getError();
			ArrayList<Cluster> temp = new ArrayList<Cluster>();
			for(Cluster cluster : clusters) {
				if(cluster.getSquaredError() > 0.01) {
					FuzzyEm fe = new FuzzyEm(cluster.getArticles());
					fe.init();
					fe.start();
					if(isFirstIter) {
						System.out.println("첫 iter: 클러스터 개수=" + fe.getClusters().size());
					}
					temp.addAll(fe.getClusters());
				} else {
					temp.add(cluster);
				}
			}
			clusters = temp;
			afterError = getError();
			isFirstIter = false;
		}
	}

	private double getError() {
		double sum = 0;
		for(Cluster cluster : clusters) {
			sum += cluster.getSquaredError();
		}
		return Math.sqrt(sum);
	}

	private boolean isEnough() {
		boolean isEnough = false;
		if(Math.sqrt(beforeError-afterError) < 0.1) {
			isEnough = true;
		} else {
			isEnough = false;
		}
		return isEnough;
	}

	public void printResult() {
		System.out.println("총 클러스터 수=" + clusters.size());
		for(Cluster cluster : clusters) {
			System.out.println(cluster.toString());
			System.out.println();
		}
	}

	public ArrayList<Cluster> getClusters() {
		return clusters;
	}
}