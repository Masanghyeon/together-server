package kr.co.nexters.together.common.datastore;

import kr.co.nexters.together.protocol.models.MArticle;
import org.hibernate.Session;

public class ArticleQuery extends Query<MArticle> {

    public ArticleQuery(Session session) {
        this(session, 0, DEFAULT_LIMIT);
    }

    public ArticleQuery(Session session, int skip, int limit) {
        setCriteria(session.createCriteria(MArticle.class));
        setSkip(skip);
        setLimit(limit);
    }

}
