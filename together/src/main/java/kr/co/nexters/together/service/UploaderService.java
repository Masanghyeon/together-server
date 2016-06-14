package kr.co.nexters.together.service;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Inject;
import kr.co.nexters.together.common.config.S3Configuration;
import kr.co.nexters.together.common.config.TogetherConfiguration;
import kr.co.nexters.together.common.service.AbstractService;
import kr.co.nexters.together.common.token.TokenManager;
import kr.co.nexters.together.common.token.UploadToken;
import kr.co.nexters.together.common.util.S3Client;
import kr.co.nexters.together.protocol.dto.response.UploadImageResult;
import kr.co.nexters.together.protocol.models.MImage;
import kr.co.nexters.together.protocol.models.MUser;
import kr.co.nexters.together.protocol.models.S3Key;
import kr.co.nexters.together.utils.ImageResizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UploaderService extends AbstractService {
    private S3Client s3Client;
    private TokenManager<UploadToken> tokenManager;
    private S3Configuration s3Configuration;
    private TogetherConfiguration togetherConfiguration;
    private ImageResizer imageResizer;
    private final ExecutorService executorService =
            Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("PhotoUploaderS3ThreadPool-%d").build());

    @Inject
    public UploaderService(S3Client s3Client,
                           TokenManager<UploadToken> tokenManager,
                           S3Configuration s3Configuration,
                           TogetherConfiguration togetherConfiguration,
                           ImageResizer imageResizer) {
        this.s3Client = s3Client;
        this.tokenManager = tokenManager;
        this.s3Configuration = s3Configuration;
        this.togetherConfiguration = togetherConfiguration;
        this.imageResizer = imageResizer;
    }

    public UploadImageResult uploadImage(MUser user, File file) throws Exception {
        List<File> tempFiles = Lists.newArrayList();
        try {
            String renamedFileName = user.getId() + "_" + UUID.randomUUID().toString() + ".jpg";
            File renamedFile = renameUploadedFile(file, renamedFileName);

            // TODO: add resize module
//            List<MImage> resizedFilePaths = imageResizer.resize(renamedFile.getAbsolutePath(), renamedFile.getAbsolutePath().substring(0, renamedFile.getAbsolutePath().indexOf(".jpg")));
            BufferedImage bimg = ImageIO.read(renamedFile);
            MImage img = new MImage().setWidth(bimg.getWidth()).setHeight(bimg.getHeight()).setSource(renamedFile.getAbsolutePath());
            List<MImage> resizedFilePaths = Lists.newArrayList(img);
            List<MImage> uploadedImages = Lists.newArrayList();
            List<Future<Void>> futures = Lists.newArrayList();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateDirectory = dateFormat.format(new Date());
            // S3 upload
            for (MImage resizedImage : resizedFilePaths) {
                tempFiles.add(new File(resizedImage.getSource()));
                String[] splitedFileName = resizedImage.getSource().split("/");
                String fileName = splitedFileName[splitedFileName.length - 1];
                S3Key key = new S3Key(s3Configuration.getBucketName(), Joiner.on("/").join(dateDirectory, fileName));
                futures.add(executorService.submit(() -> {
                    s3Client.createObject(key, renamedFile, CannedAccessControlList.PublicRead).waitForUploadResult();
                    return null;
                }));
                URL source = s3Client.getUrl(key);
                uploadedImages.add(new MImage().setWidth(resizedImage.getWidth()).setHeight(resizedImage.getHeight()).setSource(source.toString()));
            }
            for (Future future : futures) {
                future.get();
            }

            // make token
            UploadToken uploadToken = new UploadToken();
            uploadToken.setImages(uploadedImages);
            String token = tokenManager.sign(uploadToken, togetherConfiguration.getUploadToken().getExpirySeconds());
            UploadImageResult result = new UploadImageResult();
            result.setUploadToken(token);
            return result;
        } catch (Exception exception) {
            throw exception;
        } finally {
            tempFiles.forEach(tempFile -> tempFile.delete());
        }
    }

    public File renameUploadedFile(File file, String renamedName) throws Exception {
        File newFile = new File("./temp/" + renamedName);
        Files.move(file, newFile);
        return newFile;
    }


}
