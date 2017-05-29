package seo.dale.raddit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Topic {

    @JsonIgnore
    private final AtomicInteger upCounter;
    @JsonIgnore
    private final AtomicInteger downCounter;

    private String id;
    private String content;

    @Setter(AccessLevel.NONE)
    private int ups;
    @Setter(AccessLevel.NONE)

    private int downs;
    private Date date;

    public Topic() {
        this("", 0, 0);
    }

    public Topic(String content) {
        this(content, 0, 0);
    }

    public Topic(String content, int ups, int downs) {
        id = UUID.randomUUID().toString();
        date = new Date();
        upCounter = new AtomicInteger(ups);
        downCounter = new AtomicInteger(downs);
        this.ups = ups;
        this.downs = downs;
        this.content = content;
    }

    public void incUp() {
        ups = upCounter.incrementAndGet();
    }

    public void incDown() {
        downs = downCounter.incrementAndGet();
    }

}
