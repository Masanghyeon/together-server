package kr.co.nexters.together.module;

import com.google.inject.AbstractModule;
import kr.co.nexters.together.utils.ImageResizer;
import kr.co.nexters.together.utils.WandImageResizer;

public class ResizerModule extends AbstractModule {
    private ImageResizer imageResizer;

    public ResizerModule() {
        this.imageResizer = new WandImageResizer();
    }

    @Override
    protected void configure() {
        bind(ImageResizer.class).toInstance(imageResizer);
    }
}
