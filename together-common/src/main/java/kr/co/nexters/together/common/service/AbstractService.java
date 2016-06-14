package kr.co.nexters.together.common.service;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import kr.co.nexters.together.common.PagingList;
import kr.co.nexters.together.common.datastore.Datastore;
import kr.co.nexters.together.common.datastore.QueryResult;
import kr.co.nexters.together.common.token.PageToken;
import kr.co.nexters.together.common.token.TokenManager;
import kr.co.nexters.together.protocol.dto.PagingDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public abstract class AbstractService {
    private SessionFactory sessionFactory;
    private Datastore datastore;
    private TokenManager<PageToken> pageTokenManager;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Inject
    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    @Inject
    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

    @Inject
    public void setPageTokenManager(TokenManager<PageToken> pageTokenManager) {
        this.pageTokenManager = pageTokenManager;
    }

    public Session beginTransaction() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public void commitAndClose(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    public int getPosition(String pageToken) throws Exception{
        if (Strings.isNullOrEmpty(pageToken)) {
            return 0;
        }
        return pageTokenManager.verify(pageToken).getPosition();
    }

    public <E> PagingList<E> toPagingList(QueryResult<E> queryResult) {
        PagingList<E> pagingList = new PagingList<>();
        pagingList.setResults(queryResult.getResults());

        PagingDTO pagingDTO = new PagingDTO();
        if (queryResult.getSkip() + queryResult.getLimit() < queryResult.getTotalCount()) {
            PageToken token = new PageToken().setPosition(queryResult.getSkip() + queryResult.getLimit()).setCreatedAt(new DateTime(DateTimeZone.UTC).getMillis());
            pagingDTO.setNextToken(pageTokenManager.sign(token, Integer.MAX_VALUE));
        }
        pagingDTO.setTotalCount(queryResult.getTotalCount());
        pagingList.setPaging(pagingDTO);
        return pagingList;
    }
}
