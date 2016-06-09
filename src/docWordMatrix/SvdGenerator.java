package docWordMatrix;

import java.util.ArrayList;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import data.Article;
import data.Point;


public class SvdGenerator {
	private SingularValueDecomposition svd;
	private double[][] u;


	public SvdGenerator(double[][] matrix) {
		double[][] input = matrix;
		RealMatrix inputMatrix = new Array2DRowRealMatrix(input);
		svd = new SingularValueDecomposition(inputMatrix);
		u = svd.getU().getData();
	}

	public void setArticlePoint(ArrayList<Article> articles) {
		int dimension = 5;
		for(int i = 0; i < svd.getU().getRowDimension(); i++) {
			ArrayList<Double> point = new ArrayList<Double>();
			for(int j = 0; j < dimension; j++) {
				point.add(u[i][j]);
			}
			articles.get(i).setPoint(new Point(point));
		}
	}
}
