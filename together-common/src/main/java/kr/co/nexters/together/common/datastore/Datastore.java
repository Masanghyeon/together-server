package kr.co.nexters.together.common.datastore;

public interface Datastore {
    public <E> QueryResult<E> one(Query<E> query);

    public <E> QueryResult<E> list(Query<E> query);

    public <E> QueryResult<E> add(E element);

    public <E> QueryResult<E> delete(E element);
}
