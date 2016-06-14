package kr.co.nexters.together.protocol.models;

import kr.co.nexters.together.protocol.CharacterType;
import kr.co.nexters.together.protocol.FacebookStatus;
import kr.co.nexters.together.protocol.Gender;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="users")
public class MUser {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private String email;
    private String password;
    private String nickname;
    private Gender gender;
    private Integer age;
    private String description;
    private CharacterType characterType;
    @ManyToMany(mappedBy = "users")
    private Set<MTravelPreference> travelPreferences;
    private FacebookStatus facebookStatus;
    @OneToOne(cascade = CascadeType.ALL)
    private MProfilePhoto profilePhoto;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MArticle> articles;
    private Long createdAt;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<MReview> reviews;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reviews_written_by_me")
    private Set<MReview> reviewsWrittenByMe;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }

    public FacebookStatus getFacebookStatus() {
        return facebookStatus;
    }

    public void setFacebookStatus(FacebookStatus facebookStatus) {
        this.facebookStatus = facebookStatus;
    }

    public MProfilePhoto getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(MProfilePhoto profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Set<MReview> getReviews() {
        return reviews;
    }

    public void setReviews(Set<MReview> reviews) {
        this.reviews = reviews;
    }

    public Set<MReview> getReviewsWrittenByMe() {
        return reviewsWrittenByMe;
    }

    public void setReviewsWrittenByMe(Set<MReview> reviewsWrittenByMe) {
        this.reviewsWrittenByMe = reviewsWrittenByMe;
    }

    public Set<MTravelPreference> getTravelPreferences() {
        return travelPreferences;
    }

    public void setTravelPreferences(Set<MTravelPreference> travelPreferences) {
        this.travelPreferences = travelPreferences;
    }

    public Set<MArticle> getArticles() {
        return articles;
    }

    public void setArticles(Set<MArticle> articles) {
        this.articles = articles;
    }
}
