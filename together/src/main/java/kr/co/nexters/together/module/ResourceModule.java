package kr.co.nexters.together.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import kr.co.nexters.together.resource.AccessTokenResource;
import kr.co.nexters.together.resource.ArticleResource;
import kr.co.nexters.together.resource.ArticlesResource;
import kr.co.nexters.together.resource.InfoResource;
import kr.co.nexters.together.resource.PingResource;
import kr.co.nexters.together.resource.RegistrationResource;
import kr.co.nexters.together.resource.ReviewResource;
import kr.co.nexters.together.resource.UploaderResource;
import kr.co.nexters.together.resource.UserResource;

public class ResourceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccessTokenResource.class).in(Singleton.class);
        bind(ArticleResource.class).in(Singleton.class);
        bind(ArticlesResource.class).in(Singleton.class);
        bind(PingResource.class).in(Singleton.class);
        bind(AccessTokenResource.class).in(Singleton.class);
        bind(InfoResource.class).in(Singleton.class);
        bind(RegistrationResource.class).in(Singleton.class);
        bind(ReviewResource.class).in(Singleton.class);
        bind(UploaderResource.class).in(Singleton.class);
        bind(UserResource.class).in(Singleton.class);
    }
}
