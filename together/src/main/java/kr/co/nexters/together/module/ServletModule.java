package kr.co.nexters.together.module;

import com.google.common.collect.Maps;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.Map;

public class ServletModule extends JerseyServletModule {
    @Override
    protected void configureServlets() {
        bind(GuiceContainer.class);
        Map<String, String> params = Maps.newHashMap();
        params.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
        serve("/*").with(GuiceContainer.class, params);
    }
}
