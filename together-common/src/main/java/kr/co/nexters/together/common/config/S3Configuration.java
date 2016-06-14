package kr.co.nexters.together.common.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.File;
import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class S3Configuration implements Configuration {
    public static S3Configuration populate(File file) throws Exception {
        S3Configuration conf = new ObjectMapper().readValue(file, S3Configuration.class);
        conf.verify();
        return conf;
    }

    private String bucketName;
    private URL s3EndPoint;

    @Override
    public void verify() throws Exception {
        Preconditions.checkArgument(bucketName != null, "bucketName must not be null.");
        Preconditions.checkArgument(s3EndPoint != null, "s3Endpoint must not be null.");
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public URL getS3EndPoint() {
        return s3EndPoint;
    }

    public void setS3EndPoint(URL s3EndPoint) {
        this.s3EndPoint = s3EndPoint;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bucketName", bucketName)
                .add("s3EndPoint", s3EndPoint)
                .toString();
    }
}
