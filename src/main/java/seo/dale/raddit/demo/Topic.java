package seo.dale.raddit.demo;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Topic {

    private UUID id;
    private String content;
    private int ups;
    private int downs;
    private Date date;

}
