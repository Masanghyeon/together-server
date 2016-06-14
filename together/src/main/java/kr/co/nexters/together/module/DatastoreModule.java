package kr.co.nexters.together.module;

import com.google.inject.AbstractModule;
import kr.co.nexters.together.common.datastore.Datastore;
import kr.co.nexters.together.common.datastore.TogetherDatastore;
import kr.co.nexters.together.common.hibernate.HibernateUtils;
import org.hibernate.SessionFactory;

public class DatastoreModule extends AbstractModule {
    private SessionFactory sessionFactory;
    private Datastore datastore;

    public DatastoreModule() throws Exception {
        this.sessionFactory = HibernateUtils.createSessionFactory();
        this.datastore = new TogetherDatastore();
    }

    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(sessionFactory);
        bind(Datastore.class).toInstance(datastore);
    }
}
