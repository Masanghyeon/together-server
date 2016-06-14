package kr.co.nexters.together.common.datastore;

import java.util.List;

public class QueryResult<E> {
    private E result;
    private List<E> results;
    private int skip;
    private int limit;
    private long totalCount;

    public E getResult() {
        return result;
    }

    public void setResult(E result) {
        this.result = result;
    }

    public List<E> getResults() {
        return results;
    }

    public void setResults(List<E> results) {
        this.results = results;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
