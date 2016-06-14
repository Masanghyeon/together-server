package kr.co.nexters.together.common.module;

import com.auth0.jwt.JWTSigner;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import kr.co.nexters.together.common.config.SecretConfiguration;
import kr.co.nexters.together.common.config.TogetherConfiguration;
import kr.co.nexters.together.common.token.AccessToken;
import kr.co.nexters.together.common.token.PageToken;
import kr.co.nexters.together.common.token.PojoTokenManager;
import kr.co.nexters.together.common.token.TokenManager;
import kr.co.nexters.together.common.token.UploadToken;

public class TokenModule extends AbstractModule {
    @Override
    protected void configure() {}

    @Singleton
    @Provides
    private TokenManager<AccessToken> provideAccessTokenManager(TogetherConfiguration togetherConfiguration) {
        SecretConfiguration accessToken = togetherConfiguration.getAccessToken();
        JWTSigner.Options options = new JWTSigner.Options().setJwtId(false).setIssuedAt(true).setExpirySeconds(accessToken.getExpirySeconds());
        return new PojoTokenManager<AccessToken>(accessToken.getSecretKey().getBytes(), AccessToken.class, options);
    }

    @Singleton
    @Provides
    private TokenManager<UploadToken> provideUploadTokenManager(TogetherConfiguration togetherConfiguration) {
        SecretConfiguration uploadToken= togetherConfiguration.getUploadToken();
        JWTSigner.Options options = new JWTSigner.Options().setJwtId(false).setIssuedAt(true).setExpirySeconds(uploadToken.getExpirySeconds());
        return new PojoTokenManager<UploadToken>(uploadToken.getSecretKey().getBytes(), UploadToken.class, options);
    }

    @Singleton
    @Provides
    private TokenManager<PageToken> providePageTokenManager(TogetherConfiguration togetherConfiguration) {
        SecretConfiguration pageToken= togetherConfiguration.getPageToken();
        JWTSigner.Options options = new JWTSigner.Options().setJwtId(false).setIssuedAt(true).setExpirySeconds(pageToken.getExpirySeconds());
        return new PojoTokenManager<PageToken>(pageToken.getSecretKey().getBytes(), PageToken.class, options);
    }
}
