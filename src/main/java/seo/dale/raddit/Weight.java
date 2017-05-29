package seo.dale.raddit;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Weight Model
 * - used to keep topic IDS sorted by upvotes in a sorted set
 */
@Data
public class Weight {

    private int ups;

    private String id;

    public Weight(int ups, String id) {
        this.ups = ups;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
