package seo.dale.raddit;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Topic Repository In-memory Implementation
 */
@Component
public class TopicRepositoryMemory implements TopicRepository {

    private final Map<String, Topic> topicMap;

    public TopicRepositoryMemory() {
        this(new HashMap<>());
    }

    public TopicRepositoryMemory(Map<String, Topic> topicMap) {
        this.topicMap = topicMap;
    }

    @Override
    public Topic save(Topic topic) {
        topicMap.put(topic.getId(), topic);
        return topic;
    }

    @Override
    public Topic findOne(String id) {
        return topicMap.getOrDefault(id, null);
    }

    @Override
    public List<Topic> findAll() {
        return new ArrayList<>(topicMap.values());
    }

    @Override
    public Long count() {
        return Long.valueOf(topicMap.size());
    }

}
