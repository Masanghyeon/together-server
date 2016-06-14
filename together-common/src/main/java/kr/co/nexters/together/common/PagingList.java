package kr.co.nexters.together.common;

import com.google.common.collect.ForwardingList;
import kr.co.nexters.together.protocol.dto.PagingDTO;

import java.util.List;

public class PagingList<E> extends ForwardingList<E> {
    List<E> results;
    PagingDTO paging;

    public List<E> getResults() {
        return results;
    }

    public PagingList<E> setResults(List<E> results) {
        this.results = results;
        return this;
    }

    public PagingDTO getPaging() {
        return paging;
    }

    public PagingList<E> setPaging(PagingDTO paging) {
        this.paging = paging;
        return this;
    }

    @Override
    protected List<E> delegate() {
        return results;
    }
}
