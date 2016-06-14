package kr.co.nexters.together.utils;

import kr.co.nexters.together.protocol.models.MImage;

import java.util.List;

public interface ImageResizer {

    List<MImage> resize(String sourcePath, String outputPath) throws Exception;
}
