package kr.co.nexters.together.common.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import kr.co.nexters.together.common.exception.TogetherExceptionMapper;

public class ExceptionMapperModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TogetherExceptionMapper.class).in(Singleton.class);
    }
}
