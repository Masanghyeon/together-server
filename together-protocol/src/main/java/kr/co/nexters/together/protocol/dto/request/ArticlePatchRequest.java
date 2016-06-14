package kr.co.nexters.together.protocol.dto.request;

import kr.co.nexters.together.protocol.TravelPreference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class ArticlePatchRequest {
    private Double latitude;
    private Double longitude;
    private String content;
    private Long regionId;
    private List<TravelPreference> travelPreferences;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public List<TravelPreference> getTravelPreferences() {
        return travelPreferences;
    }

    public void setTravelPreferences(List<TravelPreference> travelPreferences) {
        this.travelPreferences = travelPreferences;
    }
}
