package kr.co.nexters.together.common.token;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import kr.co.nexters.together.protocol.models.MImage;

import java.util.List;
import java.util.Map;

public class UploadToken implements PojoToken {
    private List<MImage> images;

    public Map<String, Object> serialize() {
        Map<String, Object> result = Maps.newHashMap();
        result.put("images", images);
        return result;
    }

    public void deseialize(Map<String, Object> verifiedToken) {
        images = Lists.newArrayList();
        for (Map<String, Object> img : (List<Map<String, Object>>)verifiedToken.get("images")) {
            images.add(new MImage()
                    .setWidth((Integer)img.get("width"))
                    .setHeight((Integer)img.get("height"))
                    .setSource((String)img.get("source")));
        }
    }

    public List<MImage> getImages() {
        return images;
    }

    public void setImages(List<MImage> images) {
        this.images = images;
    }
}
