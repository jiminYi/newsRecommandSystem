package docWordMatrix;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnector;
import data.Article;

public class DocWordMatrixGenerator {
	private double[][] matrix;
	private ArrayList<Article> articles;
	private ArrayList<Article> userArticles;
	private ArrayList<String> wordList;
	private DBConnector db;
	private String userId;

	public DocWordMatrixGenerator(String userId) throws SQLException {
		db = new DBConnector();
		db.executeUpdate("use newsdb");
		this.userId = userId;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public ArrayList<Article> getUserArticles() {
		return userArticles;
	}

	public void generate() throws SQLException {
		setArticles();
		setUserArticles();
		setMartix();
		setArticlePoint();
	}

	private void printArticles() {
		System.out.println("*********article*********");
		for(Article article : articles) {
			System.out.println(article.toString());
		}
		System.out.println("*********user article*********");
		for(Article article : userArticles) {
			System.out.println(article.toString());
		}
	}

	private void setArticles() throws SQLException {
		articles = new ArrayList<Article>();
		wordList = new ArrayList<String>();
		ResultSet rs = db.executeQuery("SELECT * FROM article ORDER BY timestamp");
		while(rs.next()) {
			Article newArticle = new Article(rs.getString(1), rs.getString(2), rs.getString(3));
			articles.add(newArticle);
			for(String word : newArticle.getKeyword()) {
				if(!wordList.contains(word)) {
					wordList.add(word);
				}
			}
		}
	}

	private void setUserArticles() throws SQLException {
		userArticles = new ArrayList<Article>();
		ResultSet rs = db.executeQuery("SELECT * FROM user_article WHERE user_id='" + userId + "' ORDER BY timestamp");
		while(rs.next()) {
			Article newArticle = new Article(rs.getString(1));
			int index = articles.indexOf(newArticle);
			if(index == -1) {
				System.out.println(rs.getString(1));
			} else {
				userArticles.add(articles.get(index));
			}
		}
	}

	private void setMartix() {
		int sizeOfArticles = articles.size();
		int sizeOfWords = wordList.size();
		matrix = new double[sizeOfArticles][sizeOfWords];
		for(int i = 0; i < sizeOfArticles; i++) {
			for(int j = 0; j < sizeOfWords; j++) {
				if(articles.get(i).getKeyword().contains(wordList.get(j))) {
					matrix[i][j] = 1;
				}
			}
		}
	}

	private void setArticlePoint() {
		EjmlSvdGenerator svd = new EjmlSvdGenerator(matrix);
		svd.setArticlePoint(articles);
	}
}
