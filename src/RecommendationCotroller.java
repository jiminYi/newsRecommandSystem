import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Article;
import kmean.KMean;
import recommendation.RecommendationModule;
import docWordMatrix.DocWordMatrixGenerator;
import fuzzyEm.FuzzyEm;
import fuzzyEm.FuzzyEmController;


public class RecommendationCotroller {
	
	public static void main(String args[]) throws SQLException, IOException, ClassNotFoundException {
		ArrayList<String> url_title = new ArrayList<String>();
		RecommendationCotroller recommendationCotroller = new RecommendationCotroller();
		url_title = recommendationCotroller.recommend("yu");
		System.out.println("추천 결과: ");
		for(String url :  url_title) {
			//System.out.println(url);
		}
	}
	
	public ArrayList<String> recommend(String id) throws SQLException, IOException, ClassNotFoundException {
		long start = System.currentTimeMillis() ;
		if(id == null) { 
			System.out.println("사용법: 추천받고자 하는 유저의 아이디 넣어라잉~!");
			return null;
		}
		DocWordMatrixGenerator generator = new DocWordMatrixGenerator(id);
		generator.generate();
		System.out.println("***************************************************************");
		FuzzyEmController articleClustering = new FuzzyEmController(generator.getArticles());
		articleClustering.clustering();
		System.out.println("***************************************************************");
		FuzzyEmController userArticleClustering = new FuzzyEmController(generator.getUserArticles());
		userArticleClustering.clustering();
		userArticleClustering.printResult();
		System.out.println("***************************************************************");
		RecommendationModule recommendationModule = new RecommendationModule(articleClustering.getClusters(), userArticleClustering.getClusters());
		recommendationModule.init();
		recommendationModule.recommend();
		long end = System.currentTimeMillis(); 
		System.out.println((end-start)/1000 +" 초 걸림");
		
		return recommendationModule.getResult();
	}
}
