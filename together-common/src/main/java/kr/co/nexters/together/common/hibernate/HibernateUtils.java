package kr.co.nexters.together.common.hibernate;

import kr.co.nexters.together.protocol.models.MArticle;
import kr.co.nexters.together.protocol.models.MImage;
import kr.co.nexters.together.protocol.models.MProfilePhoto;
import kr.co.nexters.together.protocol.models.MRegion;
import kr.co.nexters.together.protocol.models.MRegionGroup;
import kr.co.nexters.together.protocol.models.MReview;
import kr.co.nexters.together.protocol.models.MTravelPreference;
import kr.co.nexters.together.protocol.models.MUser;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

public class HibernateUtils {
    public static SessionFactory createSessionFactory() throws Exception {
        File configurationFile = new File("./config/hibernate.cfg.xml");
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(configurationFile).build();
        SessionFactory sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(MArticle.class)
                .addAnnotatedClass(MImage.class)
                .addAnnotatedClass(MProfilePhoto.class)
                .addAnnotatedClass(MRegion.class)
                .addAnnotatedClass(MRegionGroup.class)
                .addAnnotatedClass(MReview.class)
                .addAnnotatedClass(MTravelPreference.class)
                .addAnnotatedClass(MUser.class)
                .buildMetadata().buildSessionFactory();
        return sessionFactory;
    }
}
