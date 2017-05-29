package seo.dale.raddit.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicRepositoryMemory implements TopicRepository {

    private Map<String, Topic> topicMap;

    public TopicRepositoryMemory() {
        this(new HashMap<>());
    }

    public TopicRepositoryMemory(Map<String, Topic> topicMap) {
        this.topicMap = topicMap;
    }

    @Override
    public Topic save(Topic topic) {
        return null;
    }

    @Override
    public Topic findOne(String id) {
        return null;
    }

    @Override
    public List<Topic> findAll() {
        return null;
    }

    @Override
    public Long count() {
        return Long.valueOf(topicMap.size());
    }

}
