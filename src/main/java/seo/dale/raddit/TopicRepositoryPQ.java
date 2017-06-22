package seo.dale.raddit;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Topic Repository In-memory Implementation
 */
@Component
public class TopicRepositoryPQ implements TopicRepository {

    /**
     * Map to maintain all the topic details in memory
     */
    private final Map<String, Topic> topicMap;

    public TopicRepositoryPQ() {
        this(new HashMap<>());
    }

    public TopicRepositoryPQ(Map<String, Topic> initialMap) {
        topicMap = Collections.synchronizedMap(initialMap);
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

        Comparator<Topic> comparator = Comparator.comparing(Topic::getUps);
        Queue<Topic> queue = new PriorityQueue<>(size, comparator);
        topicMap.values().forEach(topic -> {
           if (queue.size() < size) {
               queue.offer(topic);
           } else if (comparator.compare(queue.peek(), topic) < 0) {
               queue.poll();
               queue.offer(topic);
           }
        });

        LinkedList<Topic> topN = new LinkedList<>();
        while (!queue.isEmpty()) {
            topN.addFirst(queue.poll());
        }

        return topN;
    }

    /**
     * Upvoting affects sorting. Therefore, should keep topics sorted even when upvotes change
     */
    @Override
    public void upvote(String id) {
        Topic topic = findOne(id);
        topic.incUp();
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
