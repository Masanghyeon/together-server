package kr.co.nexters.together.common.datastore;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public abstract class Query<T> {
    static final int DEFAULT_LIMIT = 32;
    private Criteria criteria;
    private Integer skip;
    private Integer limit;

    public Criteria getCriteria() {
        return criteria;
    }

    public Query setCriteria(Criteria criteria) {
        this.criteria = criteria;
        return this;
    }

    public int getSkip() {
        return skip;
    }

    public Query setSkip(Integer skip) {
        if (skip != null) this.skip = skip;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public Query setLimit(Integer limit) {
        if (limit != null) this.limit = limit;
        return this;
    }

    public void addRestriction(Criterion criterion) {
        criteria.add(criterion);
    }

    public void addOrder(Order order) {
        criteria.addOrder(order);
    }
}
