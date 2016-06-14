package kr.co.nexters.together.protocol.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="regions")
public class MRegion {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private String name;
    private Double latitude;
    private Double longitude;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<MArticle> articles;

    public MRegion() {}

    public MRegion(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void addArticle(MArticle article) {
        articles.add(article);
    }

    public void removeArticle(MArticle article) {
        articles.remove(article);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<MArticle> getArticles() {
        return articles;
    }

    public void setArticles(Set<MArticle> articles) {
        this.articles = articles;
    }
}
