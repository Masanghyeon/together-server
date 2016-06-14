package kr.co.nexters.together;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import kr.co.nexters.together.common.config.TogetherConfiguration;
import kr.co.nexters.together.protocol.TravelPreference;
import kr.co.nexters.together.protocol.models.MRegion;
import kr.co.nexters.together.protocol.models.MRegionGroup;
import kr.co.nexters.together.protocol.models.MTravelPreference;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

public class Together {

    private Server server;
    private Injector injector;

    public Together(Injector injector) throws Exception {
        this.injector = injector;
        init();
    }

    private void init() {
        server = new Server();
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        servletContextHandler.addFilter(GuiceFilter.class, "/*", null);
        servletContextHandler.addServlet(DefaultServlet.class, "/");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setResourceBase(".");
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{
                servletContextHandler,
                resourceHandler
        });

        final TogetherConfiguration togetherConfiguration = injector.getInstance(TogetherConfiguration.class);
        // get configuration instance
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(togetherConfiguration.getPort());
        server.setHandler(handlers);
        server.setConnectors(new Connector[]{
                connector
        });

        initNecessaryData();
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void join() throws Exception {
        server.join();
    }

    public void initNecessaryData() {
        SessionFactory sessionFactory = injector.getInstance(SessionFactory.class);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // 여행 성향 setting
        for (TravelPreference travelPreference : TravelPreference.values()) {
            if (session.createCriteria(MTravelPreference.class).add(Restrictions.eq("value", travelPreference)).uniqueResult() != null) {
                continue;
            }
            MTravelPreference preference = new MTravelPreference();
            preference.setValue(travelPreference);
            session.save(preference);
        }

        // 지역 setting
        Map<String, List<MRegion>> regionGroups = Maps.newHashMap();
        regionGroups.put("서울", Lists.newArrayList(
                new MRegion("서울역", 37.5551069, 126.9685024),
                new MRegion("용산역", 37.52989, 126.9625863)
        ));
        regionGroups.put("전라북도", Lists.newArrayList(
                new MRegion("남원역", 35.411288, 127.3591793),
                new MRegion("익산역", 35.940285, 126.9441843),
                new MRegion("전주역", 35.84977, 127.1596093),
                new MRegion("정읍역", 35.575723, 126.8408173)
        ));
        regionGroups.put("전라남도", Lists.newArrayList(
                new MRegion("순천역", 34.945789, 127.5010713),
                new MRegion("여수엑스포역", 34.752888, 127.7465773),
                new MRegion("광양", 34.969181, 127.5875323)
        ));
//        regionGroups.put("경상북도", Lists.newArrayList(
//                new MRegion("안동역", 1.0, 1.0),
//                new MRegion("영월역", 1.0, 1.0)
//        ));
        regionGroups.put("경상남도", Lists.newArrayList(
                new MRegion("부산역", 35.114979, 129.0393603),
                new MRegion("마산역", 35.236121, 128.5749853),
                new MRegion("진주역", 35.150432, 128.1160583)
        ));
        for (String key : regionGroups.keySet()) {
            if (session.createCriteria(MRegionGroup.class).add(Restrictions.eq("name", key)).uniqueResult() != null) {
                continue;
            }
            MRegionGroup group = new MRegionGroup();
            group.setName(key);
            group.setRegions(Sets.newHashSet());
            for (MRegion region : regionGroups.get(key)) {
                if (session.createCriteria(MRegion.class).add(Restrictions.eq("name", region.getName())).uniqueResult() != null) {
                    continue;
                }
                MRegion newRegion = new MRegion(region.getName(), region.getLatitude(), region.getLongitude());
                session.save(newRegion);
                group.getRegions().add(newRegion);
            }
            session.save(group);
        }
        session.getTransaction().commit();
        session.close();
    }
}
