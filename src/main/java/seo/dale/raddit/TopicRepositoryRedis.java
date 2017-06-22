package seo.dale.raddit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Topic Repository Redis Implementation
 */
@Component
public class TopicRepositoryRedis implements TopicRepository {

    private static final String REDIS_KEY = "topics";

    private Map<String, Topic> topicMap;

    private BoundZSetOperations<String, String> zSetOperations;

    @Autowired
    public TopicRepositoryRedis(RedisOperations<String, String> redisOperations) {
        zSetOperations = redisOperations.boundZSetOps(REDIS_KEY);
        zSetOperations.removeRange(0, -1);
        setTopicMap(new HashMap<>());
    }

    public void setTopicMap(Map<String, Topic> initialMap) {
        topicMap = Collections.synchronizedMap(initialMap);

        if (topicMap.isEmpty()) return;

        Set<ZSetOperations.TypedTuple<String>> tuples = topicMap
                .values()
                .stream()
                .map(topic -> new DefaultTypedTuple<>(topic.getId(), (double) topic.getUps()))
                .collect(Collectors.toSet());

        zSetOperations.add(tuples);
    }

    @Override
    public Topic save(Topic topic) {
        topicMap.put(topic.getId(), topic);
        zSetOperations.add(topic.getId(), topic.getUps());
        return topicMap.get(topic.getId());
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

    @Override
    public List<Topic> findTopN(int size) {
        return zSetOperations.reverseRange(0, size - 1)
                .stream()
                .map(topicMap::get)
                .collect(Collectors.toList());
    }

}