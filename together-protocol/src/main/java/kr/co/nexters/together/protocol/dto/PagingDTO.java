package kr.co.nexters.together.protocol.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class PagingDTO {
    private String prevToken;
    private String nextToken;
    private long totalCount;

    public String getPrevToken() {
        return prevToken;
    }

    public void setPrevToken(String prevToken) {
        this.prevToken = prevToken;
    }

    public String getNextToken() {
        return nextToken;
    }

    public PagingDTO setNextToken(String nextToken) {
        this.nextToken = nextToken;
        return this;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public PagingDTO setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        return this;
    }
}
