package sort;

import java.util.Comparator;

import data.Cluster;

public class ClusterSizeCompare implements Comparator<Cluster> {
	@Override
	public int compare(Cluster cluster0, Cluster cluster1) {
		return cluster0.getSize() > cluster1.getSize() ? -1 : cluster0.getSize() < cluster1.getSize() ? 1:0;
	}
}
