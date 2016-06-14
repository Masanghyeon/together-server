package kr.co.nexters.together.protocol.models;

import kr.co.nexters.together.protocol.TravelPreference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="travel_preferences")
public class MTravelPreference {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    private TravelPreference value;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<MUser> users;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<MArticle> articles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TravelPreference getValue() {
        return value;
    }

    public void setValue(TravelPreference value) {
        this.value = value;
    }

    public Set<MUser> getUsers() {
        return users;
    }

    public void setUsers(Set<MUser> users) {
        this.users = users;
    }

    public Set<MArticle> getArticles() {
        return articles;
    }

    public void setArticles(Set<MArticle> articles) {
        this.articles = articles;
    }
}
