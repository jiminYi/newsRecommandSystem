package sort;

import java.util.Comparator;

import data.Cluster;

public class ClusterDistanceCompare implements Comparator<Cluster> {
	@Override
	public int compare(Cluster cluster0, Cluster cluster1) {
		return cluster0.getDistance() < cluster1.getDistance() ? -1 : cluster0.getDistance() > cluster1.getDistance() ? 1:0;
	}
}
