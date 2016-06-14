package kr.co.nexters.together.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import kr.co.nexters.together.service.AccessTokenService;
import kr.co.nexters.together.service.ArticleService;
import kr.co.nexters.together.service.ArticlesService;
import kr.co.nexters.together.service.RegistrationService;
import kr.co.nexters.together.service.ReviewService;
import kr.co.nexters.together.service.ReviewsService;
import kr.co.nexters.together.service.UploaderService;
import kr.co.nexters.together.service.UserService;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccessTokenService.class).in(Singleton.class);
        bind(ArticleService.class).in(Singleton.class);
        bind(ArticlesService.class).in(Singleton.class);
        bind(RegistrationService.class).in(Singleton.class);
        bind(ReviewService.class).in(Singleton.class);
        bind(ReviewsService.class).in(Singleton.class);
        bind(UserService.class).in(Singleton.class);
        bind(UploaderService.class).in(Singleton.class);
    }
}
