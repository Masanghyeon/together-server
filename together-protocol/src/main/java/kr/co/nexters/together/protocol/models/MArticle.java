package kr.co.nexters.together.protocol.models;

import kr.co.nexters.together.protocol.ArticleState;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "articles")
public class MArticle {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private ArticleState state;
    private String content;
    private String location;
    private Double latitude;
    private Double longitude;
    @ManyToOne
    private MUser author;
    @ManyToOne
    private MRegion region;
    @ManyToMany(mappedBy = "articles")
    private Set<MTravelPreference> travelPreferences;
    private Long createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArticleState getState() {
        return state;
    }

    public void setState(ArticleState state) {
        this.state = state;
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

    public MUser getAuthor() {
        return author;
    }

    public void setAuthor(MUser author) {
        this.author = author;
    }

    public MRegion getRegion() {
        return region;
    }

    public void setRegion(MRegion region) {
        this.region = region;
    }

    public Set<MTravelPreference> getTravelPreferences() {
        return travelPreferences;
    }

    public void setTravelPreferences(Set<MTravelPreference> travelPreferences) {
        this.travelPreferences = travelPreferences;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
