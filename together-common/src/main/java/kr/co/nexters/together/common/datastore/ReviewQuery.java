package kr.co.nexters.together.common.datastore;

import kr.co.nexters.together.protocol.models.MReview;
import org.hibernate.Session;

public class ReviewQuery extends Query<MReview> {

    public ReviewQuery(Session session) {
        this(session, 0, DEFAULT_LIMIT);
    }

    public ReviewQuery(Session session, int skip, int limit) {
        setCriteria(session.createCriteria(MReview.class));
        setSkip(skip);
        setLimit(limit);
    }

}
