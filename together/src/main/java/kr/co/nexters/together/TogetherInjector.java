package kr.co.nexters.together;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import kr.co.nexters.together.common.config.S3Configuration;
import kr.co.nexters.together.common.config.TogetherConfiguration;
import kr.co.nexters.together.common.module.ExceptionMapperModule;
import kr.co.nexters.together.common.module.S3Module;
import kr.co.nexters.together.common.module.TogetherConfigurationModule;
import kr.co.nexters.together.common.module.TokenModule;
import kr.co.nexters.together.module.DatastoreModule;
import kr.co.nexters.together.module.ResizerModule;
import kr.co.nexters.together.module.ResourceModule;
import kr.co.nexters.together.module.ServiceModule;
import kr.co.nexters.together.module.ServletModule;

import java.io.File;

public class TogetherInjector {

    public static Injector createInjector(TogetherConfiguration configuration) throws Exception {
        Module module = Modules.combine(
                createModules(configuration),
                createS3Module());
        Injector injector = Guice.createInjector(module);
        return injector;
    }

    public static Module createModules(TogetherConfiguration configuration) throws Exception {
        return Modules.combine(
                new TogetherConfigurationModule(configuration),
                new ExceptionMapperModule(),
                new TokenModule(),
                new ServletModule(),
                new ResourceModule(),
                new ServiceModule(),
                new DatastoreModule(),
                new ResizerModule()
        );
    }

    public static Module createS3Module() throws Exception {
        AWSCredentials awsCredentials = new PropertiesCredentials(new File("./config/awsCredentials.properties"));
        S3Configuration s3Configuration = S3Configuration.populate(new File("./config/s3.json"));
        return new S3Module(awsCredentials, s3Configuration);
    }
}
