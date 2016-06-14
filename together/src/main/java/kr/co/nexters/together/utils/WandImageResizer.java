package kr.co.nexters.together.utils;

import com.google.common.collect.Lists;
import kr.co.nexters.together.protocol.models.MImage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class WandImageResizer implements ImageResizer {
    private final File RESIZER = new File("./resizer.py");

    @Override
    public List<MImage> resize(String sourcePath, String outputPath) throws Exception {
        List<String> command = Lists.newArrayList();
        command.addAll(Arrays.asList("python", RESIZER.getAbsolutePath(), sourcePath, outputPath));
        Process process = new ProcessBuilder(command)
                // stderr 출력이 현재 프로세스에 그대로 출력돼서 로깅될 수 있게 한다.
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start();
        process.waitFor();
//        BufferedReader stdOut = new BufferedReader( new InputStreamReader(process.getInputStream()) );
//        String line = "";
//        while( (line = stdOut.readLine()) != null ) {
//            System.out.println(line);
//        }
        return Lists.newArrayList();
    }
}
