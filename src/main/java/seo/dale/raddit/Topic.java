package seo.dale.raddit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Topic Model
 */
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

    public Weight weight() {
        return new Weight(ups, id);
    }

    public Topic clone() {
        Topic topic = new Topic(content, ups, downs);
        topic.id = this.id;
        topic.date = this.date;
        return topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        if (ups != topic.ups) return false;
        if (downs != topic.downs) return false;
        if (!id.equals(topic.id)) return false;
        if (content != null ? !content.equals(topic.content) : topic.content != null) return false;
        return date != null ? date.equals(topic.date) : topic.date == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + ups;
        result = 31 * result + downs;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

}
