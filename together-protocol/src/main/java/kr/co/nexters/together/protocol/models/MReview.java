package kr.co.nexters.together.protocol.models;

import kr.co.nexters.together.protocol.ReviewType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class MReview {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private String content;
    private ReviewType type;

    @ManyToOne
    private MUser author;

    @ManyToOne
    private MUser to;
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

    public ReviewType getType() {
        return type;
    }

    public void setType(ReviewType type) {
        this.type = type;
    }

    public MUser getAuthor() {
        return author;
    }

    public void setAuthor(MUser author) {
        this.author = author;
    }

    public MUser getTo() {
        return to;
    }

    public void setTo(MUser to) {
        this.to = to;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
