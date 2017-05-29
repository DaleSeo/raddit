package seo.dale.raddit.demo;

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

    public Topic(String content) {
        this();
        this.content = content;
    }

    public Topic() {
        id = UUID.randomUUID().toString();
        date = new Date();
    }

}
