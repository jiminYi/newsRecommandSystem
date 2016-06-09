package sort;

import java.util.Comparator;

import data.Article;

public class ArticleDistanceCompare implements Comparator<Article> {
	@Override
	public int compare(Article article0, Article article1) {
		return article0.getDistance() < article1.getDistance() ? -1 : article0.getDistance() > article1.getDistance() ? 1:0;
	}
}
