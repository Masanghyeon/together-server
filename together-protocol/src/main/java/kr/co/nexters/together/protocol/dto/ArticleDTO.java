package kr.co.nexters.together.protocol.dto;

import kr.co.nexters.together.protocol.TravelPreference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class ArticleDTO {
    private long id;
    private String content;
    private String location;
    private Double latitude;
    private Double longitude;
    private CompactUserDTO author;
    private RegionDTO region;
    private List<TravelPreference> travelPreferences;
    private Long createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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

    public CompactUserDTO getAuthor() {
        return author;
    }

    public void setAuthor(CompactUserDTO author) {
        this.author = author;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    public List<TravelPreference> getTravelPreferences() {
        return travelPreferences;
    }

    public void setTravelPreferences(List<TravelPreference> travelPreferences) {
        this.travelPreferences = travelPreferences;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
