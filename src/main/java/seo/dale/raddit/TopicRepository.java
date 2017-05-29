package seo.dale.raddit;

import java.util.List;

/**
 * TopicRepository Interface
 */
public interface TopicRepository {

    /**
     * save a topic
     */
    Topic save(Topic topic);

    /**
     * find a topic
     */
    Topic findOne(String id);

    /**
     * find all topics
     */
    List<Topic> findAll();

    /**
     * count the number of topics
     */
    Long count();

}
