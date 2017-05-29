package seo.dale.raddit;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Topic {

    private String id;
    private String content;
    private int ups;
    private int downs;
    private Date date;

    public Topic(String content, int ups, int downs) {
        this(content);
        this.ups = ups;
        this.downs = downs;
    }

    public Topic(String content) {
        this();
        this.content = content;
    }

    public Topic() {
        id = UUID.randomUUID().toString();
        date = new Date();
    }

}
