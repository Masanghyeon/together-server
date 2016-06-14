package kr.co.nexters.together;

import kr.co.nexters.together.common.config.TogetherConfiguration;

import java.io.File;
public class TogetherMain {

    public static void main(String[] args) throws Exception {
        TogetherConfiguration configuration = TogetherConfiguration.populate(new File("./config/together.json"));
        Together together = new Together(TogetherInjector.createInjector(configuration));
        together.start();
    }
}
