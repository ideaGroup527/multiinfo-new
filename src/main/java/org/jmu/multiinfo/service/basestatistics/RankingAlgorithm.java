package org.jmu.multiinfo.service.basestatistics;

public interface RankingAlgorithm {
    /**
     * <p>Performs a rank transformation on the input data, returning an array
     * of ranks.</p>
     *
     * <p>Ranks should be 1-based - that is, the smallest value
     * returned in an array of ranks should be greater than or equal to one,
     * rather than 0. Ranks should in general take integer values, though
     * implementations may return averages or other floating point values
     * to resolve ties in the input data.</p>
     *
     * @param data array of data to be ranked
     * @return an array of ranks corresponding to the elements of the input array
     */
    double[] rank (double[] data);
}
