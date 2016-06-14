package kr.co.nexters.together.common.datastore;

import org.hibernate.criterion.Projections;

import java.util.List;

public class TogetherDatastore implements Datastore {

    @Override
    public <E> QueryResult<E> one(Query<E> query) {
        E element = (E)query.getCriteria().uniqueResult();
        QueryResult<E> queryResult = new QueryResult<>();
        queryResult.setResult(element);
        return queryResult;
    }

    @Override
    public <E> QueryResult<E> list(Query<E> query) {
        List<E> list = query.getCriteria().setFirstResult(query.getSkip()).setMaxResults(query.getLimit()).list();
        QueryResult<E> queryResult = new QueryResult<>();
        queryResult.setResults(list);
        queryResult.setSkip(query.getSkip());
        queryResult.setLimit(query.getLimit());
        queryResult.setTotalCount((Long)query.getCriteria().setProjection(Projections.rowCount()).uniqueResult());
        return queryResult;
    }

    @Override
    public <E> QueryResult<E> add(E element) {
        return null;
    }

    @Override
    public <E> QueryResult<E> delete(E element) {
        return null;
    }
}
