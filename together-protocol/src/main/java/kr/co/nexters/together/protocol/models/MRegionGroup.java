package kr.co.nexters.together.protocol.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="region_groups")
public class MRegionGroup {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<MRegion> regions;

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

    public Set<MRegion> getRegions() {
        return regions;
    }

    public void setRegions(Set<MRegion> regions) {
        this.regions = regions;
    }
}
