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
        Topic found = findOne(topic.getId());
        if (found != null) {
            weightSet.remove(found.weight()); // remove the old weight form sorted set
        }

        topicMap.put(topic.getId(), topic);
        weightSet.add(topic.weight()); // also add weight to sorted set
        return topic;
    }

    @Override
    public Topic findOne(String id) {
        Topic topic = topicMap.get(id);
        return topic != null ? topic.clone() : null;
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
//        // Just sort: O(nlog(n))
//        return topicMap
//                .values()
//                .stream()
//                .sorted(Comparator.comparing(Topic::getUps).reversed())
//                .limit(size)
//                .collect(Collectors.toList());

//        // Min heap: O(nlog(k))
//        Comparator<Topic> comparator = Comparator.comparing(Topic::getUps);
//        Queue<Topic> queue = new PriorityQueue<>(size, comparator);
//        topicMap.values().forEach(topic -> {
//            if (queue.size() < size) {
//                queue.offer(topic);
//            } else if (comparator.compare(queue.peek(), topic) < 0) {
//                queue.poll();
//                queue.offer(topic);
//            }
//        });
//
//        LinkedList<Topic> topN = new LinkedList<>();
//        while (!queue.isEmpty()) {
//            topN.addFirst(queue.poll());
//        }
//
//        return topN;
        return weightSet
                .stream()
                .limit(size)
                .map(Weight::getId)
                .map(topicMap::get)
                .collect(Collectors.toList());
    }

}
