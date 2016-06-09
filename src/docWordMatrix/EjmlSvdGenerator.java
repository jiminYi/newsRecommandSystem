package docWordMatrix;

import java.util.ArrayList;

import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.DecompositionFactory;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;
import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.SimpleSVD;

import data.Article;
import data.Point;

public class EjmlSvdGenerator {
	private DenseMatrix64F u;

	public static void main(String args[]) {
		double[][] matrixValue = {
				{1, 1, 0},
				{0, 0, 1}
		};
		DenseMatrix64F matrix = new DenseMatrix64F(matrixValue);
		SingularValueDecomposition<DenseMatrix64F> svd = 
				DecompositionFactory.svd(matrix.numRows, matrix.numCols, true, true, true);
		if(!DecompositionFactory.decomposeSafe(svd, matrix)) {
		    System.err.println("Decomposition failed");
		    return;
		}
		System.out.println("******* U *****");
		DenseMatrix64F u = svd.getU(null, false);
		for(int i = 0; i < u.getNumRows(); i++) {
			for(int j = 0; j < u.getNumCols(); j++) {
				System.out.print(u.get(i, j) + " ");
			}
			System.out.println();
		}
		System.out.println("******* S *****");
		DenseMatrix64F s = svd.getW(null);
		for(int i = 0; i < s.getNumRows(); i++) {
			for(int j = 0; j < s.getNumCols(); j++) {
				System.out.print(s.get(i, j) + " ");
			}
			System.out.println();
		}
		System.out.println("******* VT *****");
		DenseMatrix64F vt = svd.getV(null, false);
		for(int i = 0; i < vt.getNumRows(); i++) {
			for(int j = 0; j < vt.getNumCols(); j++) {
				System.out.print(vt.get(i, j) + " ");
			}
			System.out.println();
		}
	}

	public EjmlSvdGenerator(double[][] matrixValue) {
		DenseMatrix64F matrix = new DenseMatrix64F(matrixValue);
		SingularValueDecomposition<DenseMatrix64F> svd = 
				DecompositionFactory.svd(matrix.numRows, matrix.numCols, true, true, true);
		if(!DecompositionFactory.decomposeSafe(svd, matrix)) {
		    System.err.println("Decomposition failed");
		    return;
		}
		u = svd.getU(null, false);
	}

	public void setArticlePoint(ArrayList<Article> articles) {
		int dimension = 5;
		for(int i = 0; i < u.getNumRows(); i++) {
			ArrayList<Double> point = new ArrayList<Double>();
			for(int j = 0; j < dimension; j++) {
				point.add(u.get(i, j));
			}
			articles.get(i).setPoint(new Point(point));
		}
	}
}
