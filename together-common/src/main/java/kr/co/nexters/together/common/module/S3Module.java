package kr.co.nexters.together.common.module;

import com.amazonaws.auth.AWSCredentials;
import com.google.inject.AbstractModule;
import kr.co.nexters.together.common.config.S3Configuration;
import kr.co.nexters.together.common.util.S3Client;

public class S3Module extends AbstractModule {
    private S3Client s3Client;
    private S3Configuration s3Configuration;

    public S3Module(AWSCredentials credentials, S3Configuration s3Configuration) {
        this.s3Client = new S3Client(credentials, s3Configuration);
        this.s3Configuration = s3Configuration;
    }

    @Override
    protected void configure() {
        bind(S3Client.class).toInstance(this.s3Client);
        bind(S3Configuration.class).toInstance(this.s3Configuration);
    }
}
