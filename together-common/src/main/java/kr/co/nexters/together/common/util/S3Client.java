package kr.co.nexters.together.common.util;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.StorageClass;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.google.common.base.Joiner;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import kr.co.nexters.together.common.config.S3Configuration;
import kr.co.nexters.together.protocol.models.S3Key;

import java.io.File;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class S3Client {
    private S3Configuration s3Configuration;
    private AmazonS3Client amazonS3Client;
    private TransferManager tx;

    public S3Client(AWSCredentials credentials, S3Configuration s3Configuration) {
        System.setProperty(SDKGlobalConfiguration.ENABLE_S3_SIGV4_SYSTEM_PROPERTY, "true");
        this.s3Configuration = s3Configuration;
        this.amazonS3Client = new AmazonS3Client(credentials);
        this.amazonS3Client.setEndpoint(s3Configuration.getS3EndPoint().toString());
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat(s3Configuration.getBucketName() + "S3Client-Transfer-Thread-%d").build();
        this.tx = new TransferManager(amazonS3Client, Executors.newFixedThreadPool(10, threadFactory));
    }

    public S3Client(S3Configuration s3Configuration) {
        this.s3Configuration = s3Configuration;
    }

    public Upload createObject(S3Key s3Key, File file, CannedAccessControlList list) {
        String bucketName = s3Key.getBucketName();
        String key = s3Key.getKey();
        PutObjectRequest request = new PutObjectRequest(bucketName, key, file);
        request.setStorageClass(StorageClass.Standard);
        request.setCannedAcl(list);
        return this.tx.upload(request);
    }

    public URL getUrl(S3Key s3Key) throws Exception {
        return new URL(Joiner.on("/").skipNulls().join(
                s3Configuration.getS3EndPoint(),
                s3Key.getBucketName(),
                s3Key.getKey())
        );
    }
}
