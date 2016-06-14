package kr.co.nexters.together.common.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import kr.co.nexters.together.common.config.SecretConfiguration;
import kr.co.nexters.together.common.config.SecretKey;
import kr.co.nexters.together.common.config.TogetherConfiguration;

public class TogetherConfigurationModule extends AbstractModule {
    private final TogetherConfiguration configuration;

    public TogetherConfigurationModule(TogetherConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(TogetherConfiguration.class).toInstance(this.configuration);
        bind(SecretConfiguration.class).annotatedWith(Names.named(SecretKey.PASSWORD)).toInstance(this.configuration.getPassword());
        bind(SecretConfiguration.class).annotatedWith(Names.named(SecretKey.ACCESS_TOKEN)).toInstance(this.configuration.getAccessToken());
        bind(SecretConfiguration.class).annotatedWith(Names.named(SecretKey.UPLOAD_TOKEN)).toInstance(this.configuration.getUploadToken());
    }
}
