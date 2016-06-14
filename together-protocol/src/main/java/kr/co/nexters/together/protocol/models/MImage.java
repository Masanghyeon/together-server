package kr.co.nexters.together.protocol.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class MImage {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private int width;
    private int height;
    private String source;

    public int getWidth() {
        return width;
    }

    public MImage setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public MImage setHeight(int height) {
        this.height = height;
        return this;
    }

    public String getSource() {
        return source;
    }

    public MImage setSource(String source) {
        this.source = source;
        return this;
    }
}
