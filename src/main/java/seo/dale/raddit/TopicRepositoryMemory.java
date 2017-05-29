package seo.dale.raddit;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Topic Repository In-memory Implementation
 */
@Component
public class TopicRepositoryMemory implements TopicRepository {

    /**
     * Map to maintain all the topic details in memory
     */
    private final Map<String, Topic> topicMap;

    /**
     * Sorted Set to keep topic IDs sorted by upvotes
     */
    private SortedSet<Weight> weightSet;

    public TopicRepositoryMemory() {
        this(new HashMap<>());
    }

    public TopicRepositoryMemory(Map<String, Topic> initialMap) {
        topicMap = Collections.synchronizedMap(initialMap);

        // Set is sorted by upvotes in descending order
        // If two upvotes are the same, they will be sorted by ids
        weightSet = Collections.synchronizedSortedSet(
                new TreeSet<>(
                    Comparator
                        .comparing(Weight::getUps)
                        .thenComparing(Weight::getId)
                        .reversed()
                )
        );

        // load sorted set
        topicMap.values().stream()
                .forEach(topic -> weightSet.add(topic.weight()));
    }

    @Override
    public Topic save(Topic topic) {
        topicMap.put(topic.getId(), topic);
        weightSet.add(topic.weight()); // also add weight to sorted set
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

    /**
     * Instead of sorting topics every time it is invoked,
     * get the already sorted topic ids from weight set and find topics form topic map.
     */
    @Override
    public List<Topic> findTopN(int size) {
//        return topicMap
//                .values()
//                .stream()
//                .sorted(Comparator.comparing(Topic::getUps).reversed())
//                .limit(size)
//                .collect(Collectors.toList());
        return weightSet
                .stream()
                .limit(size)
                .map(Weight::getId)
                .map(topicMap::get)
                .collect(Collectors.toList());
    }

    /**
     * Upvoting affects sorting. Therefore, should keep topics sorted even when upvotes change
     */
    @Override
    public void upvote(String id) {
        Topic topic = findOne(id);
        weightSet.remove(topic.weight()); // remove old weight
        topic.incUp();
        weightSet.add(topic.weight()); // add new weight
    }

    /**
     * Downvoting don't affect sorting
     */
    @Override
    public void downvote(String id) {
        Topic topic = findOne(id);
        topic.incDown();
    }

}
