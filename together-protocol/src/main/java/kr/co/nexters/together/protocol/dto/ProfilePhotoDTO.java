package kr.co.nexters.together.protocol.dto;

import java.util.List;

public class ProfilePhotoDTO {
    private List<ImageDTO> images;

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }
}
