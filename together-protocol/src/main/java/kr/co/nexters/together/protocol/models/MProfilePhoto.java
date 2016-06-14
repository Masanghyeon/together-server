package kr.co.nexters.together.protocol.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "profile_photos")
public class MProfilePhoto {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<MImage> images;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<MImage> getImages() {
        return images;
    }

    public void setImages(Set<MImage> images) {
        this.images = images;
    }
}
