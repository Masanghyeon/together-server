package kr.co.nexters.together.protocol.dto;

import kr.co.nexters.together.protocol.ReviewType;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class UserReviewDTO {
    private long id;
    private String content;
    private ReviewType type;
    private CompactUserDTO author;
    private CompactUserDTO to;
    private long createdAt;

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

    public CompactUserDTO getAuthor() {
        return author;
    }

    public void setAuthor(CompactUserDTO author) {
        this.author = author;
    }

    public CompactUserDTO getTo() {
        return to;
    }

    public void setTo(CompactUserDTO to) {
        this.to = to;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
